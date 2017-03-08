/*
Navicat MySQL Data Transfer

Source Server         : mysql5.6
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : seckilldb

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-03-07 21:33:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存Id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('54', '戴尔新游匣笔记本', '100', '2017-03-05 17:32:47', '2017-03-07 17:32:51', '2017-03-04 17:32:57');
INSERT INTO `seckill` VALUES ('55', '创意潮炫智能装备', '99', '2017-03-07 17:34:30', '2017-03-07 23:34:34', '2017-03-07 21:23:18');
INSERT INTO `seckill` VALUES ('56', '荣耀V9', '99', '2017-03-07 17:35:20', '2017-03-08 17:35:29', '2017-03-07 21:05:37');
INSERT INTO `seckill` VALUES ('57', '荣耀8青春版', '50', '2017-03-09 17:35:47', '2017-03-18 17:35:51', '2017-03-07 17:35:55');
INSERT INTO `seckill` VALUES ('58', '中兴V8新品', '80', '2017-03-04 17:36:23', '2017-03-06 17:36:27', '2017-03-03 17:36:35');
INSERT INTO `seckill` VALUES ('59', 'iPhone 6 32G新品', '60', '2017-03-08 17:36:53', '2017-03-11 17:37:02', '2017-03-07 17:37:05');
INSERT INTO `seckill` VALUES ('60', 'vivo X9Plus星空灰', '75', '2017-03-09 17:37:22', '2017-03-19 17:37:26', '2017-03-05 17:37:29');
INSERT INTO `seckill` VALUES ('61', ' Meitu/美图 T8', '65', '2017-03-03 17:37:42', '2017-03-24 17:37:47', '2017-02-16 17:37:52');
INSERT INTO `seckill` VALUES ('62', '魅族魅蓝note5', '86', '2017-03-02 17:38:12', '2017-03-15 17:38:17', '2017-03-07 21:22:06');
INSERT INTO `seckill` VALUES ('63', 'OPPO R9s Plus黑色版', '99', '2017-03-11 17:39:17', '2017-03-19 17:39:21', '2017-03-02 17:39:26');
INSERT INTO `seckill` VALUES ('64', '烘干神器 智能款 极速烘干', '10', '2017-03-08 21:01:40', '2017-03-09 21:01:55', '2017-03-01 21:02:00');
INSERT INTO `seckill` VALUES ('65', '单板弗拉门戈吉他古典吉他缺角电箱', '50', '2017-03-08 00:02:55', '2017-03-16 21:03:13', '2017-03-01 21:03:16');
INSERT INTO `seckill` VALUES ('66', '30寸34寸36寸39寸初学者入门', '54', '2017-03-24 21:04:01', '2017-03-31 21:04:07', '2017-03-02 21:04:14');
INSERT INTO `seckill` VALUES ('67', '芦荟海藻眼胶60ml眼霜眼膜', '100', '2017-03-07 21:07:51', '2017-03-18 21:07:56', '2017-03-03 21:07:59');
INSERT INTO `seckill` VALUES ('68', '腋下干爽止汗石除臭', '99', '2017-03-07 21:08:29', '2017-03-25 21:08:37', '2017-03-02 21:08:42');
INSERT INTO `seckill` VALUES ('69', '电动吹箫一体式男用', '65', '2017-03-07 21:10:13', '2017-03-08 21:10:18', '2017-03-02 21:10:22');
INSERT INTO `seckill` VALUES ('70', '日本充气娃娃', '45', '2017-03-06 21:10:53', '2017-03-09 21:10:57', '2017-03-07 21:11:15');
INSERT INTO `seckill` VALUES ('71', '男用范冰冰cqww', '10', '2017-03-06 21:12:00', '2017-03-08 21:12:11', '2017-03-07 21:12:16');

-- ----------------------------
-- Table structure for success_killed
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` int(11) NOT NULL COMMENT '秒杀产品Id',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号码',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态:-1:无效、0:成功、1:已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`,`user_phone`),
  CONSTRAINT `success_killed_ibfk_1` FOREIGN KEY (`seckill_id`) REFERENCES `seckill` (`seckill_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of success_killed
-- ----------------------------
INSERT INTO `success_killed` VALUES ('55', '18702604785', '0', '2017-03-07 21:23:18');
INSERT INTO `success_killed` VALUES ('56', '15270998540', '0', '2017-03-07 17:41:24');
INSERT INTO `success_killed` VALUES ('62', '18702604785', '0', '2017-03-07 21:22:06');

-- ----------------------------
-- Procedure structure for excuteSeckillPro
-- ----------------------------
DROP PROCEDURE IF EXISTS `excuteSeckillPro`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `excuteSeckillPro`(IN userPhone VARCHAR(11),In seckillId INT(20),IN killTime TIMESTAMP,OUT result INT)
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
					set result = -2;
				END IF;
		ELSEIF(rowCount=0) THEN  
			ROLLBACK;
			SET result = -1;-- 重复秒杀
		ELSE										
			ROLLBACK;
			set result = -2;-- 内部错误
		END IF;
END
;;
DELIMITER ;
