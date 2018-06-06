package com.leo.mvp.net.bean;

import com.leo.mvp.base.bean.BaseBean;

/**
 * created by Leo on 2018/6/6 23 : 04
 */


public class LoginBean extends BaseBean<LoginBean.Data>{
    public class Data{
        private String id;
        private String user_name;
        private String password;
        private String email;
        private String create_date;
        private String permission_id;
        private String head_img;
        private boolean isAuto;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getPermission_id() {
            return permission_id;
        }

        public void setPermission_id(String permission_id) {
            this.permission_id = permission_id;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public boolean isAuto() {
            return isAuto;
        }

        public void setAuto(boolean auto) {
            isAuto = auto;
        }
    }
}
