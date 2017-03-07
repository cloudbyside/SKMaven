/*存储过程：秒杀
#使用存储过程执行秒杀
#@userPhone 用户手机
#@seckillId 产品编号
#@killTime	 秒杀时间
#@result		 秒杀结果(-3:库存为空 -2:内部错误 -1重复秒杀: 1:秒杀成功)
#处理流程：先插入购买明细，重复秒杀则回滚，否则再插入购买明细，如果库存为空，那么回滚
*/

DROP PROCEDURE IF EXISTS `excuteSeckillPro`;
CREATE PROCEDURE excuteSeckillPro(IN userPhone VARCHAR(11),In seckillId INT(20),IN killTime TIMESTAMP,OUT result INT)
BEGIN
		DECLARE rowCount INT DEFAULT 0;
		START TRANSACTION;
		INSERT IGNORE INTO success_killed(seckill_id,user_phone,status,create_time) VALUES(seckillId,userPhone,0,killTime);
		SELECT ROW_COUNT() INTO rowCount;
		IF(rowCount>0) THEN	-- 插入成功
			  UPDATE seckill SET number = number -1 WHERE seckill_id = seckillId AND start_time < killTime AND end_time > killTime AND number > 0;-- 更新库存
				SELECT ROW_COUNT() INTO rowCount;
				IF(rowCount>0) THEN
					COMMIT;
					SET result = 1;-- 库存减少成功
				ELSEIF(rowCount=0) THEN
					SET result = -3;-- 库存为空,或者秒杀时间非法
          ROLLBACK;
				ELSE
					ROLLBACK;
					SET result = -2;
				END IF;
		ELSEIF(rowCount=0) THEN
			ROLLBACK;
			SET result = -1;-- 重复秒杀
		ELSE
			ROLLBACK;
			SET result = -2;-- 内部错误
		END IF;
END
