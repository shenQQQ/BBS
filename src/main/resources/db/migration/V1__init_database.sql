USE `bbs`;
ALTER DATABASE bbs CHARACTER SET utf8;

DROP TABLE IF EXISTS `ad`;

CREATE TABLE `ad` (
                      `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                      `url` VARCHAR(255) DEFAULT NULL,
                      PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;


INSERT  INTO `ad`(`id`,`url`) VALUES
(1,'https://image.gcores.com/a509f972-0787-4ae6-aede-0afbe677d023.jpg?x-oss-process=image/resize,limit_1,m_fill,w_328,h_395/quality,q_90');


DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `title` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                           `content` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                           `head_img` VARCHAR(1000) NOT NULL,
                           `in_time` DATETIME NOT NULL,
                           `modify_time` DATETIME DEFAULT NULL,
                           `user_id` INT NOT NULL,
                           `comment_count` INT NOT NULL DEFAULT '0',
                           `collect_count` INT NOT NULL DEFAULT '0',
                           `VIEW` INT NOT NULL DEFAULT '0',
                           `top` BIT(1) NOT NULL DEFAULT b'0',
                           `good` BIT(1) NOT NULL DEFAULT b'0',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `title` (`title`),
                           KEY `article_in_time` (`in_time`),
                           KEY `user_id` (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8mb4;


INSERT  INTO `article`(`id`,`title`,`content`,`head_img`,`in_time`,`modify_time`,`user_id`,`comment_count`,`collect_count`,`VIEW`,`top`,`good`) VALUES
(1,'兴趣使然的英雄','<div label-module=\"para\">&nbsp;&nbsp;&nbsp;&nbsp;埼玉，英雄名：秃头披风侠。又名一拳超人。日本<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E7%BD%91%E7%BB%9C%E6%BC%AB%E7%94%BB/14923570\">网络漫画</a>《<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E4%B8%80%E6%8B%B3%E8%B6%85%E4%BA%BA/1966105\">一拳超人</a>》及衍生同名动画中的<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E7%94%B7%E4%B8%BB%E8%A7%92/9848756\">男主角</a>。</div><div label-module=\"para\">拥有着无法估量的强大实力，力量和速度均处于本作的天花板，远超正式登场的其他所有英雄和怪人。学生时代因为自己的弱小而对自己的未来产生了怀疑。</div><div label-module=\"para\">&nbsp;&nbsp;&nbsp;&nbsp;进入社会后，一度陷入失业的颓废状态中，但在与螃蟹人的战斗中，找回了小时候想成为英雄的趣味所在而开始努力锻炼身体（每天100个<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E4%BF%AF%E5%8D%A7%E6%92%91/6202\">俯卧撑</a>、100个<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E4%BB%B0%E5%8D%A7%E8%B5%B7%E5%9D%90/4149026\">仰卧起坐</a>、100个<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E6%B7%B1%E8%B9%B2/4197189\">深蹲</a>、10公里<a target=\"_blank\" href=\"https://baike.baidu.com/item/%E9%95%BF%E8%B7%91/9089232\">长跑</a>，并且再热也不能开空调），并在锻炼身体的过程中击败各式各样的怪人。</div><div label-module=\"para\">&nbsp;&nbsp;&nbsp;&nbsp;3年后，埼玉拥有了绝对强大的力量，作为代价他失去了很多（像恐惧之类的感情在不断磨灭，头发掉光）。注：激情应该是因为没有遇到旗鼓相当的对手而没有表现出来。</div><p style=\"text-align: center;\"><img src=\"http://shenqqq.top:8080/static/upload/topic/shenqi/937df5fb-4c38-4ac4-aa40-5b34a49a5499.jpeg?v=018057\" style=\"max-width:30%;\"/><br/></p>','https://image.gcores.com/0b706070-b0b1-4900-9b3e-3df355843cd4.png?x-oss-process=image/quality,q_90/watermark,image_d2F0ZXJtYXJrLnBuZw,g_se,x_10,y_10','2022-02-06 15:38:06','2022-05-11 10:55:47',1,3,6,3,'','');

DROP TABLE IF EXISTS `article_tag`;

CREATE TABLE `article_tag` (
                               `tag_id` INT NOT NULL,
                               `article_id` INT NOT NULL,
                               KEY `tag_id` (`tag_id`),
                               KEY `article_id` (`article_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Data for the table `article_tag` */

INSERT  INTO `article_tag`(`tag_id`,`article_id`) VALUES
(1,1);

/*Table structure for table `collect` */

DROP TABLE IF EXISTS `collect`;

CREATE TABLE `collect` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `article_id` INT NOT NULL,
                           `user_id` INT NOT NULL,
                           `in_time` DATETIME NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `article_id` (`article_id`),
                           KEY `user_id` (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1692 DEFAULT CHARSET=utf8mb4;

/*Data for the table `collect` */

INSERT  INTO `collect`(`id`,`article_id`,`user_id`,`in_time`) VALUES
(1,1,2,'2022-02-06 15:38:59');

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `content` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
                           `article_id` INT NOT NULL,
                           `user_id` INT NOT NULL,
                           `in_time` DATETIME NOT NULL,
                           `comment_id` INT DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `comment_in_time` (`in_time`),
                           KEY `article_id` (`article_id`),
                           KEY `user_id` (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=535 DEFAULT CHARSET=utf8mb4;

/*Data for the table `comment` */

INSERT  INTO `comment`(`id`,`content`,`article_id`,`user_id`,`in_time`,`comment_id`) VALUES
(1,'写的不错！',1,2,'2022-02-06 15:38:58',1);

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
                                `id` INT NOT NULL AUTO_INCREMENT,
                                `article_id` INT NOT NULL,
                                `user_id` INT NOT NULL,
                                `target_user_id` INT NOT NULL,
                                `ACTION` VARCHAR(255) NOT NULL DEFAULT '',
                                `in_time` DATETIME NOT NULL,
                                `isread` TINYINT(1) DEFAULT NULL,
                                `content` LONGTEXT,
                                PRIMARY KEY (`id`),
                                KEY `target_user_id` (`target_user_id`),
                                KEY `article_id` (`article_id`),
                                KEY `user_id` (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8mb4;

/*Data for the table `notification` */

INSERT  INTO `notification`(`id`,`article_id`,`user_id`,`target_user_id`,`ACTION`,`in_time`,`isread`,`content`) VALUES
(1,1,2,1,'COMMENT','2022-02-06 15:38:58',1,'zhangsan评论了你的文章 兴趣使然的英雄 说：写的不错！'),
(2,1,2,1,'COLLECT','2022-02-06 15:38:59',1,'zhangsan收藏了你的文章 兴趣使然的英雄 ');


/*Table structure for table `recommend` */

DROP TABLE IF EXISTS `recommend`;

CREATE TABLE `recommend` (
                             `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                             `article_id` INT DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

/*Data for the table `recommend` */

INSERT  INTO `recommend`(`id`,`article_id`) VALUES
(13,1);

/*Table structure for table `system_config` */

DROP TABLE IF EXISTS `system_config`;

CREATE TABLE `system_config` (
                                 `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                 `key` VARCHAR(255) DEFAULT NULL,
                                 `value` VARCHAR(255) DEFAULT '',
                                 `description` VARCHAR(1000) NOT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `key` (`key`),
                                 KEY `system_config_key` (`key`)
) ENGINE=INNODB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

/*Data for the table `system_config` */

INSERT  INTO `system_config`(`id`,`key`,`value`,`description`) VALUES
(1,'project_name','SIMPLE-BBS','项目名称'),
(2,'project_description','a simple bbs','项目描述'),
(3,'owner','Shen Qi','站长'),
(4,'owner_website','https://shenqqq.github.io/','站长个人网站'),
(5,'owner_email','1017706039@qq.com','站长邮箱'),
(6,'project_version','1.0','项目版本'),
(7,'server_address','http://localhost:8080','服务器地址'),
(8,'platform_address','http://localhost:3000','前台项目地址'),
(9,'backstage_address','http://localhost:9000','后台项目地址'),
(10,'file_upload_path','D://JAVA/BBS/static/upload/','文件存储路径'),
(11,'file_upload_address','http://localhost:8080/static/upload/','文件下载路径(localhost的地址不能被外部访问)'),
(12,'max_upload_file_num','3','富文本编辑器最大文件上传数量'),
(13,'max_upload_image_size','3','上传图片最大尺寸（MB）'),
(14,'max_upload_video_size','3','上传视频最大尺寸（MB）'),
(15,'file_upload_timeout','5001','上传文件超时时间（ms）'),
(16,'redis_host','','Redis的host地址'),
(17,'redis_port','','Redis的端口号'),
(18,'redis_password','','Redis的密码（没设置可以不填）'),
(19,'redis_database','','Redis的数据库'),
(20,'redis_timeout','','Redis的超时时间'),
(21,'websocket_address','ws://localhost:8080/websocket','websocket地址（删除则不启用）'),
(22,'elasticsearch_host','','es服务地址'),
(23,'elasticsearch_port','','es服务端口'),
(24,'elasticsearch_index','','es索引名'),
(25,'kafka_ip','119.3.8.118:9092','kafka的ip地址');

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
                       `id` INT NOT NULL AUTO_INCREMENT,
                       `name` VARCHAR(255) NOT NULL DEFAULT '',
                       `in_time` DATETIME NOT NULL,
                       PRIMARY KEY (`id`),
                       UNIQUE KEY `name` (`name`),
                       KEY `tag_name` (`name`)
) ENGINE=INNODB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tag` */

INSERT  INTO `tag`(`id`,`name`,`in_time`) VALUES
(1,'游戏','2022-04-12 11:21:10');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `username` VARCHAR(255) NOT NULL DEFAULT '',
                        `PASSWORD` VARCHAR(255) DEFAULT '',
                        `avatar` VARCHAR(1000) DEFAULT NULL,
                        `email` VARCHAR(255) DEFAULT NULL,
                        `bio` VARCHAR(1000) DEFAULT '不写slogan/bio的都是大懒蛋子',
                        `in_time` DATETIME NOT NULL,
                        `token` VARCHAR(255) NOT NULL DEFAULT '',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `token` (`token`),
                        UNIQUE KEY `username` (`username`),
                        KEY `user_email` (`email`),
                        KEY `user_in_time` (`in_time`)
) ENGINE=INNODB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

INSERT  INTO `user`(`id`,`username`,`PASSWORD`,`avatar`,`email`,`bio`,`in_time`,`token`) VALUES
(1,'shenqi','123456','https://joeschmoe.io/api/v1/73','1017706039@qq.com','一个兴趣使然的英雄','2022-01-06 15:38:06','ef431efc-dd8c-4909-a8d3-2034e61a8e5e'),
(2,'zhangsan','123456','','123@qq.com','这个人很懒','2022-02-06 15:38:06','ef431efc-dd8c-4909-a8d3-2034e61a8e5d');

