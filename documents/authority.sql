CREATE DATABASE IF NOT EXISTS authority
DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS t_module;
CREATE TABLE t_module (
  id varchar(36) NOT NULL COMMENT '模块id',
  name varchar(50) NOT NULL COMMENT '模块名称',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_authority;
CREATE TABLE t_authority (
  id varchar(36) NOT NULL COMMENT '权限ID',
  name varchar(50) NOT NULL COMMENT '权限名称',
  action varchar(255) NOT NULL COMMENT '权限对应的操作',
  module_id varchar(36) DEFAULT NULL COMMENT '模块id',
  PRIMARY KEY (id),
  KEY t_authority_id (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role (
  id varchar(36) NOT NULL COMMENT '角色ID',
  name varchar(50) NOT NULL COMMENT '角色名称',
  all_authority int(11) NOT NULL DEFAULT '0' COMMENT '是否具有所有权限',
  PRIMARY KEY (id),
  KEY t_role_id (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
  id varchar(36) NOT NULL COMMENT '用户ID',
  name varchar(50) NOT NULL COMMENT '用户名称',
  password varchar(128) NOT NULL COMMENT '用户密码',
  role_names varchar(255) DEFAULT NULL COMMENT '用户所有权限的名称，用逗号隔开',
  PRIMARY KEY (id),
  KEY t_user_id (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_authority_role;
CREATE TABLE t_authority_role (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限角色ID',
  authority_id varchar(36) NOT NULL COMMENT '权限ID',
  role_id varchar(36) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_role_user;
CREATE TABLE t_role_user (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户与角色关联ID',
  role_id varchar(36) NOT NULL COMMENT '角色ID',
  user_id varchar(36) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (id),
  KEY t_role_user_role_id (role_id),
  KEY t_role_user_user_id (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;