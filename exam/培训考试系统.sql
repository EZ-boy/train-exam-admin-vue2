/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.16 : Database - xzs
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`xzs` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `xzs`;

/*Table structure for table `k_attachment` */

DROP TABLE IF EXISTS `k_attachment`;

CREATE TABLE `k_attachment` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '附件标识',
  `rela_id` varchar(64) DEFAULT NULL COMMENT '关联标识',
  `file_name` varchar(256) DEFAULT NULL COMMENT '附件名称',
  `file_path` varchar(512) DEFAULT NULL COMMENT '存放路径',
  `file_type` varchar(18) DEFAULT NULL COMMENT '文件类型',
  `file_size` varchar(256) DEFAULT NULL COMMENT '文件大小',
  `org_no` varchar(16) DEFAULT NULL COMMENT '管理单位',
  `oper_time` datetime DEFAULT NULL COMMENT '上传时间',
  `oper_emp_no` varchar(32) DEFAULT NULL COMMENT '操作员',
  `data_oper_type` varchar(8) DEFAULT NULL COMMENT '数据操作类型，标准代码“I”插入“U”更新',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8 COMMENT='附件信息';

/*Data for the table `k_attachment` */

/*Table structure for table `k_courseware` */

DROP TABLE IF EXISTS `k_courseware`;

CREATE TABLE `k_courseware` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `major_id` int(11) DEFAULT NULL COMMENT '专业ID',
  `theme_id` int(11) DEFAULT NULL COMMENT '主题ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_no` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `file_id` varchar(255) DEFAULT NULL COMMENT '文件ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课件表';

/*Data for the table `k_courseware` */

/*Table structure for table `k_exam_ops` */

DROP TABLE IF EXISTS `k_exam_ops`;

CREATE TABLE `k_exam_ops` (
  `ops_Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模拟ID',
  `order_No` varchar(32) DEFAULT NULL COMMENT '工单编号',
  `ops_Score` varchar(255) DEFAULT NULL COMMENT '模拟成绩',
  `ops_Update` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '提交人ID',
  `ops_Update_Time` datetime DEFAULT NULL COMMENT '提交时间',
  `exam_Id` int(11) DEFAULT NULL COMMENT '考试任务ID',
  `ops_Update_Name` varchar(255) DEFAULT NULL COMMENT '提交人名称',
  PRIMARY KEY (`ops_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `k_exam_ops` */

/*Table structure for table `k_exam_paper` */

DROP TABLE IF EXISTS `k_exam_paper`;

CREATE TABLE `k_exam_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '试卷名称',
  `task_exam_id` int(11) DEFAULT NULL COMMENT '任务id',
  `paper_type` int(11) DEFAULT NULL COMMENT '试卷类型1.任务试卷',
  `frame_text_content_id` int(11) DEFAULT NULL COMMENT '关联text_content主键',
  `suggest_time` int(11) DEFAULT NULL COMMENT '考试时长',
  `deleted` varchar(8) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `question_count` int(11) DEFAULT NULL,
  `limit_start_time` datetime DEFAULT NULL,
  `limit_end_time` datetime DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8 COMMENT='试卷表';

/*Data for the table `k_exam_paper` */

/*Table structure for table `k_exam_paper_answer` */

DROP TABLE IF EXISTS `k_exam_paper_answer`;

CREATE TABLE `k_exam_paper_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_paper_id` int(11) DEFAULT NULL COMMENT '试卷ID',
  `system_score` int(11) DEFAULT NULL COMMENT '系统判定得分',
  `user_score` int(11) DEFAULT NULL COMMENT '最终得分(百分制)',
  `paper_score` int(11) DEFAULT NULL COMMENT '试卷总分',
  `question_correct` int(11) DEFAULT NULL COMMENT '做对题目数量',
  `question_count` int(11) DEFAULT NULL COMMENT '题目总数量',
  `do_time` int(11) DEFAULT NULL COMMENT '做题时间(秒)',
  `status` int(11) DEFAULT NULL COMMENT '试卷状态(1待判分 2完成)',
  `create_user` int(11) DEFAULT NULL COMMENT '答题人',
  `create_time` datetime DEFAULT NULL COMMENT '提交时间',
  `task_exam_id` int(11) DEFAULT NULL COMMENT '关联任务ID;',
  PRIMARY KEY (`id`),
  KEY `exap_paper_user` (`exam_paper_id`,`create_user`)
) ENGINE=InnoDB AUTO_INCREMENT=323 DEFAULT CHARSET=utf8 COMMENT='成绩表';

/*Data for the table `k_exam_paper_answer` */

/*Table structure for table `k_exam_paper_question_customer_answer` */

DROP TABLE IF EXISTS `k_exam_paper_question_customer_answer`;

CREATE TABLE `k_exam_paper_question_customer_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL COMMENT '题目Id',
  `exam_paper_id` int(11) DEFAULT NULL COMMENT '试卷Id',
  `exam_paper_answer_id` int(11) DEFAULT NULL COMMENT '成绩ID',
  `question_type` int(11) DEFAULT NULL COMMENT '题型',
  `major_id` int(11) DEFAULT NULL COMMENT '专业',
  `customer_score` int(11) DEFAULT NULL COMMENT '得分',
  `question_text_content_id` int(11) DEFAULT NULL COMMENT '问题内容',
  `answer` varchar(255) DEFAULT NULL COMMENT '做题答案',
  `text_content_id` int(11) DEFAULT NULL COMMENT '做题内容',
  `do_right` bit(1) DEFAULT NULL COMMENT '是否正确',
  `create_user` int(11) DEFAULT NULL COMMENT '做题人',
  `create_time` datetime DEFAULT NULL,
  `item_order` int(11) DEFAULT NULL,
  `question_score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71208 DEFAULT CHARSET=utf8 COMMENT='试卷题目答案表';

/*Data for the table `k_exam_paper_question_customer_answer` */

/*Table structure for table `k_exp_content` */

DROP TABLE IF EXISTS `k_exp_content`;

CREATE TABLE `k_exp_content` (
  `id` int(11) DEFAULT NULL COMMENT '主键id',
  `exp_id` varchar(255) DEFAULT NULL COMMENT 'id',
  `exp_title` varchar(255) DEFAULT NULL COMMENT '经验标题',
  `exp_content` mediumtext,
  `subject_id` varchar(255) DEFAULT NULL COMMENT '专业分类',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `exp_state` varchar(255) DEFAULT NULL COMMENT '状态 01 草稿  02 已发布  03 已删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `k_exp_content` */

/*Table structure for table `k_exp_eval` */

DROP TABLE IF EXISTS `k_exp_eval`;

CREATE TABLE `k_exp_eval` (
  `id` int(11) DEFAULT NULL COMMENT '主键id',
  `oper_id` varchar(255) DEFAULT NULL COMMENT 'id',
  `oper_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作类型 01评论 02点赞 03 下赞 04评分',
  `oper_content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `oper_eval` varchar(255) DEFAULT NULL COMMENT '评分',
  `exp_id` varchar(255) DEFAULT NULL COMMENT '经验id',
  `create_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人id',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `k_exp_eval` */

/*Table structure for table `k_like_comment` */

DROP TABLE IF EXISTS `k_like_comment`;

CREATE TABLE `k_like_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `file_id` varchar(64) DEFAULT NULL COMMENT '文件id',
  `user_no` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `oper_type` varchar(8) DEFAULT NULL COMMENT '操作类型：01：点赞，02：评论',
  `comment_content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='点赞评论记录表';

/*Data for the table `k_like_comment` */

/*Table structure for table `k_major` */

DROP TABLE IF EXISTS `k_major`;

CREATE TABLE `k_major` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '专业ID;',
  `major` varchar(255) DEFAULT NULL COMMENT '专业类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `oper_no` varchar(32) DEFAULT NULL COMMENT '操作人',
  `deleted` varchar(8) DEFAULT NULL COMMENT 'D删除 I插入 U更新',
  `major_type` varchar(8) DEFAULT '1' COMMENT '专业分类  01 业务 ,02 安规、03 党建',
  `major_desc` varchar(255) DEFAULT NULL COMMENT '专业描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='专业表';

/*Data for the table `k_major` */

/*Table structure for table `k_position` */

DROP TABLE IF EXISTS `k_position`;

CREATE TABLE `k_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '岗位模板名称',
  `template_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '岗位模板编码',
  `position_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务岗位名称',
  `position_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务岗位编码',
  `org_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20026 DEFAULT CHARSET=utf8;

/*Data for the table `k_position` */

/*Table structure for table `k_position_user` */

DROP TABLE IF EXISTS `k_position_user`;

CREATE TABLE `k_position_user` (
  `org_name` varchar(255) DEFAULT NULL,
  `tempalte_name` varchar(255) DEFAULT NULL,
  `tempalte_no` varchar(255) DEFAULT NULL,
  `org_no` varchar(255) DEFAULT NULL,
  `position_name` varchar(255) DEFAULT NULL,
  `position_no` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_account` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `k_position_user` */

/*Table structure for table `k_practice` */

DROP TABLE IF EXISTS `k_practice`;

CREATE TABLE `k_practice` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `do_right` int(11) DEFAULT NULL COMMENT '1对0错',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `k_practice` */

/*Table structure for table `k_question` */

DROP TABLE IF EXISTS `k_question`;

CREATE TABLE `k_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `question_type` int(11) DEFAULT NULL COMMENT '1.单选题 2.多选题 3.判断题 4.填空题 5.简答题',
  `major_id` int(11) DEFAULT NULL COMMENT '关联专业ID',
  `difficult` int(11) DEFAULT NULL COMMENT '题目难度',
  `correct` varchar(4000) DEFAULT NULL COMMENT '正确答案',
  `info_text_content_id` int(11) DEFAULT NULL COMMENT '题目 填空、 题干、解析、答案等信息关联 k_text_content',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `status` int(11) DEFAULT NULL COMMENT '1.正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` varchar(8) DEFAULT NULL COMMENT 'D删除 I插入 U更新',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `score` int(11) DEFAULT NULL COMMENT '分值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=840 DEFAULT CHARSET=utf8 COMMENT='题目表';

/*Data for the table `k_question` */

/*Table structure for table `k_study_progress` */

DROP TABLE IF EXISTS `k_study_progress`;

CREATE TABLE `k_study_progress` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '学习进度记录id',
  `file_id` varchar(64) DEFAULT NULL COMMENT '文件id',
  `user_no` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `current_time` double DEFAULT NULL COMMENT '当前时长',
  `total_time` double DEFAULT NULL COMMENT '总时长',
  `study_progress` int(11) DEFAULT NULL COMMENT '学习进度',
  `is_completed` varchar(8) DEFAULT NULL COMMENT '是否已完成：01：是：02否',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `exam_paper_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='学习进度记录表';

/*Data for the table `k_study_progress` */

/*Table structure for table `k_sys_dict` */

DROP TABLE IF EXISTS `k_sys_dict`;

CREATE TABLE `k_sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_label` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dict_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dict_value` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `dict_sort` varchar(8) DEFAULT NULL,
  `del_flag` varchar(8) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `k_sys_dict` */

insert  into `k_sys_dict`(`id`,`dict_label`,`dict_type`,`dict_value`,`dict_sort`,`del_flag`,`create_time`) values (1,'视频类型','video_type','mp4,MP4','1','0',NULL),(2,'文档类型','doc_type','doc,xls,ppt','1','0',NULL),(3,'下载题库模板url','download_question_url','http://10.161.2.241:8000/api/file/download?fileId=70','1','0',NULL),(4,'管理员','role_type','1','1','0',NULL),(7,'普通用户','role_type','2','2','0',NULL),(8,'专业1','majorType','01','1','0',NULL),(9,'专业2','majorType','02','2','0',NULL),(10,'专业1','majorType','03','3','0',NULL);

/*Table structure for table `k_task_exam` */

DROP TABLE IF EXISTS `k_task_exam`;

CREATE TABLE `k_task_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) DEFAULT NULL COMMENT '考试名称;',
  `radio_num` int(11) DEFAULT NULL COMMENT '单选数量',
  `radio_score` int(11) DEFAULT NULL COMMENT '单选分值',
  `multiple_num` int(11) DEFAULT NULL COMMENT '多选数量',
  `multiple_score` int(11) DEFAULT NULL COMMENT '多选分值',
  `actual_oper_num` int(11) DEFAULT NULL COMMENT '实操数量',
  `actual_oper_score` int(11) DEFAULT NULL COMMENT '实操分值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间;',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user_no` varchar(32) DEFAULT NULL COMMENT '创建用户;',
  `deleted` varchar(8) DEFAULT NULL COMMENT 'D删除 I插入 U更新;',
  `frame_text_content_id` int(11) DEFAULT NULL COMMENT '关联text_content主键',
  `task_desc` varchar(256) DEFAULT NULL COMMENT '描述',
  `train_ids` varchar(256) DEFAULT NULL COMMENT 'k_train表主键',
  `imitate` varchar(16) DEFAULT NULL COMMENT '01模拟',
  `majorType` varchar(255) DEFAULT NULL COMMENT '试题类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8 COMMENT='任务表';

/*Data for the table `k_task_exam` */

/*Table structure for table `k_task_exam_customer_answer` */

DROP TABLE IF EXISTS `k_task_exam_customer_answer`;

CREATE TABLE `k_task_exam_customer_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_exam_id` int(11) DEFAULT NULL COMMENT '关联任务ID;',
  `create_user` int(11) DEFAULT NULL COMMENT '考试人;',
  `create_time` datetime DEFAULT NULL COMMENT '考试时间;',
  `text_content_id` int(11) DEFAULT NULL COMMENT '任务完成情况(Json)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='用户任务表';

/*Data for the table `k_task_exam_customer_answer` */

/*Table structure for table `k_task_major` */

DROP TABLE IF EXISTS `k_task_major`;

CREATE TABLE `k_task_major` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `task_id` int(11) DEFAULT NULL COMMENT '任务id',
  `major_id` int(11) DEFAULT NULL COMMENT '专业ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='任务专业表';

/*Data for the table `k_task_major` */

/*Table structure for table `k_task_user` */

DROP TABLE IF EXISTS `k_task_user`;

CREATE TABLE `k_task_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_exam_id` int(11) DEFAULT NULL COMMENT '关联任务ID;',
  `user_id` int(11) DEFAULT NULL COMMENT '考试人;',
  `create_time` datetime DEFAULT NULL COMMENT '考试时间;',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;

/*Data for the table `k_task_user` */

/*Table structure for table `k_text_content` */

DROP TABLE IF EXISTS `k_text_content`;

CREATE TABLE `k_text_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1236 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `k_text_content` */

/*Table structure for table `k_theme` */

DROP TABLE IF EXISTS `k_theme`;

CREATE TABLE `k_theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主题ID',
  `major_id` varchar(32) DEFAULT NULL COMMENT '专业ID关联',
  `theme` varchar(32) DEFAULT NULL COMMENT '主题类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `oper_no` varchar(32) DEFAULT NULL COMMENT '更新人',
  `deleted` varchar(8) DEFAULT NULL COMMENT 'D删除 I插入 U更新',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='主题表';

/*Data for the table `k_theme` */

/*Table structure for table `k_train` */

DROP TABLE IF EXISTS `k_train`;

CREATE TABLE `k_train` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `file_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件id',
  `file_type` varchar(8) DEFAULT NULL COMMENT '文件类型：01：视频，02：文档，03：其他',
  `major_id` int(11) DEFAULT NULL COMMENT '关联专业ID',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `sub_title` varchar(255) DEFAULT NULL COMMENT '子标题',
  `study_type` varchar(8) DEFAULT NULL COMMENT '学习类型：01：必修，02：选修',
  `like_num` int(11) DEFAULT NULL COMMENT '点赞数',
  `comment_num` int(11) DEFAULT NULL COMMENT '评论数',
  `practical_operation` varchar(8) DEFAULT NULL COMMENT '实操：01：是，02：否',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `oper_no` varchar(32) DEFAULT NULL COMMENT '操作人',
  `theme_id` int(11) DEFAULT NULL COMMENT '主题id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='培训学习信息表';

/*Data for the table `k_train` */

/*Table structure for table `k_user` */

DROP TABLE IF EXISTS `k_user`;

CREATE TABLE `k_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(328) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `birth_day` datetime DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `last_active_time` datetime DEFAULT NULL,
  `deleted` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `org_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `k_user` */

insert  into `k_user`(`id`,`user_uuid`,`user_no`,`user_name`,`password`,`real_name`,`age`,`sex`,`birth_day`,`phone`,`role`,`status`,`image_path`,`create_time`,`modify_time`,`last_active_time`,`deleted`,`wx_open_id`,`org_no`) values (2,'52045f5f-a13f-4ccc-93dd-f7ee8270ad4c',NULL,'admin','D1AGFL+Gx37t0NPG4d6biYP5Z31cNbwhK5w1lUeiHB2zagqbk8efYfSjYoh1Z/j1dkiRjHU+b0EpwzCh8IGsksJjzD65ci5LsnodQVf4Uj6D3pwoscXGqmkjjpzvSJbx42swwNTA+QoDU8YLo7JhtbUK2X0qCjFGpd+8eJ5BGvk=','admin',NULL,NULL,NULL,NULL,1,NULL,NULL,'2024-11-28 17:46:49',NULL,NULL,'0',NULL,'001');

/*Table structure for table `k_user_or_position` */

DROP TABLE IF EXISTS `k_user_or_position`;

CREATE TABLE `k_user_or_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织id',
  `position_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '业务岗位编码',
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户编号',
  `delete_flag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '删除标志：0未删\r\n               1已删',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80219 DEFAULT CHARSET=utf8;

/*Data for the table `k_user_or_position` */

/*Table structure for table `o_org` */

DROP TABLE IF EXISTS `o_org`;

CREATE TABLE `o_org` (
  `org_no` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位编号',
  `org_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位名称',
  `p_org_no` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级单位编号',
  `org_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位类别',
  `sort_no` int(11) DEFAULT NULL COMMENT '排序序号',
  `org_shorthand` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位缩写'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `o_org` */

insert  into `o_org`(`org_no`,`org_name`,`p_org_no`,`org_type`,`sort_no`,`org_shorthand`) values ('001','教务处','000','03',3,'TS'),('001001','一年级','001','04',23,'QZ'),('001001001','一班','001001','06',11,NULL),('001001002','二班','001001','06',17,NULL);

/*Table structure for table `t_exam_paper` */

DROP TABLE IF EXISTS `t_exam_paper`;

CREATE TABLE `t_exam_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `paper_type` int(11) DEFAULT NULL,
  `grade_level` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `question_count` int(11) DEFAULT NULL,
  `suggest_time` int(11) DEFAULT NULL,
  `limit_start_time` datetime DEFAULT NULL,
  `limit_end_time` datetime DEFAULT NULL,
  `frame_text_content_id` int(11) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `task_exam_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_exam_paper` */

/*Table structure for table `t_exam_paper_answer` */

DROP TABLE IF EXISTS `t_exam_paper_answer`;

CREATE TABLE `t_exam_paper_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_paper_id` int(11) DEFAULT NULL,
  `paper_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `paper_type` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `system_score` int(11) DEFAULT NULL,
  `user_score` int(11) DEFAULT NULL,
  `paper_score` int(11) DEFAULT NULL,
  `question_correct` int(11) DEFAULT NULL,
  `question_count` int(11) DEFAULT NULL,
  `do_time` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `task_exam_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_exam_paper_answer` */

/*Table structure for table `t_exam_paper_question_customer_answer` */

DROP TABLE IF EXISTS `t_exam_paper_question_customer_answer`;

CREATE TABLE `t_exam_paper_question_customer_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `exam_paper_id` int(11) DEFAULT NULL,
  `exam_paper_answer_id` int(11) DEFAULT NULL,
  `question_type` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `customer_score` int(11) DEFAULT NULL,
  `question_score` int(11) DEFAULT NULL,
  `question_text_content_id` int(11) DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `text_content_id` int(11) DEFAULT NULL,
  `do_right` bit(1) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `item_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_exam_paper_question_customer_answer` */

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `send_user_id` int(11) DEFAULT NULL,
  `send_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `send_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `receive_user_count` int(11) DEFAULT NULL,
  `read_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_message` */

/*Table structure for table `t_message_user` */

DROP TABLE IF EXISTS `t_message_user`;

CREATE TABLE `t_message_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL,
  `receive_user_id` int(11) DEFAULT NULL,
  `receive_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `receive_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `readed` bit(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `read_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_message_user` */

/*Table structure for table `t_question` */

DROP TABLE IF EXISTS `t_question`;

CREATE TABLE `t_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `grade_level` int(11) DEFAULT NULL,
  `difficult` int(11) DEFAULT NULL,
  `correct` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `info_text_content_id` int(11) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_question` */

/*Table structure for table `t_subject` */

DROP TABLE IF EXISTS `t_subject`;

CREATE TABLE `t_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `level_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `item_order` int(11) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_subject` */

/*Table structure for table `t_task_exam` */

DROP TABLE IF EXISTS `t_task_exam`;

CREATE TABLE `t_task_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `grade_level` int(11) DEFAULT NULL,
  `frame_text_content_id` int(11) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `create_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_task_exam` */

/*Table structure for table `t_task_exam_customer_answer` */

DROP TABLE IF EXISTS `t_task_exam_customer_answer`;

CREATE TABLE `t_task_exam_customer_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_exam_id` int(11) DEFAULT NULL,
  `create_user` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `text_content_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_task_exam_customer_answer` */

/*Table structure for table `t_text_content` */

DROP TABLE IF EXISTS `t_text_content`;

CREATE TABLE `t_text_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_text_content` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `birth_day` datetime DEFAULT NULL,
  `user_level` int(11) DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `last_active_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `org_no` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_user` */

/*Table structure for table `t_user_event_log` */

DROP TABLE IF EXISTS `t_user_event_log`;

CREATE TABLE `t_user_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1353 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_user_event_log` */

/*Table structure for table `t_user_token` */

DROP TABLE IF EXISTS `t_user_token`;

CREATE TABLE `t_user_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `wx_open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

/*Data for the table `t_user_token` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
