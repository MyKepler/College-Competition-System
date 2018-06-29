<template>
  <div class="container">
    <!--default-active 默认激活菜单的index collapse 是否水平折叠收起菜单-->
    <el-menu default-active="1" class="el-menu-vertical" @open="handleOpen" @close="handleClose" :collapse="true">  
      <!--no-subitem表示没有子菜单-->
      <router-link :to="{name: 'Notification'}"> 
        <el-submenu index="1">
            <template slot="title" class="no-subitem">
                <i class="iconfont">&#xe710;</i>
                <i class="text">通知管理</i>
                <i class="no-subitem"></i>
            </template>
        </el-submenu>
      </router-link>
      <el-submenu index="2">
        <template slot="title" >
          <i class="iconfont">&#xe621;</i>
          <i class="text">项目管理</i>
          <i class="arrow-right"> > </i>
        </template>
        <!--name 里面的内容就是路由-->
        <router-link :to="{name: 'ProjectSignUp'}"> 
          <el-menu-item index="2-1">
            <i class="item-text">项目申请管理</i>
          </el-menu-item>
        </router-link>
        <router-link :to="{name: 'ProjectAudit'}"> 
          <el-menu-item index="2-2">
            <i class="item-text">项目报名审核</i>
          </el-menu-item>
        </router-link>
        <router-link :to="{name: 'ProjectMidManage'}">
          <el-menu-item index="2-3">
            <i class="item-text">项目中期管理</i>
          </el-menu-item>
        </router-link>
        <router-link :to="{name: 'ProjectEndManage'}">
          <el-menu-item index="2-4">
            <i class="item-text">项目结题管理</i>
          </el-menu-item>
         </router-link> 
        <router-link :to="{name: 'ProjectInfoManage'}"> 
        <el-menu-item index="2-5">
            <i class="item-text">项目信息管理</i>
          </el-menu-item>
        </router-link>
        <router-link :to="{name: 'ProjectEvaluateManage'}">
          <el-menu-item index="2-6">
            <i class="item-text">项目评审结果管理</i>
          </el-menu-item>
        </router-link>
        <router-link :to="{name: 'ProjectMemberManage'}">
          <el-menu-item index="2-7">
            <i class="item-text">项目成员管理</i>
          </el-menu-item>
        </router-link>
      </el-submenu>
      <router-link :to="{name: 'EvaluationExperts'}"> 
        <el-submenu index="3">
            <template slot="title" class="no-subitem">
              <i class="iconfont">&#xe721;</i>
              <i class="text">评审管理</i>
              <i class="no-subitem"></i>
            </template>
        </el-submenu>
      </router-link>
      <el-submenu index="4">
        <template slot="title" >
          <i class="iconfont">&#xe7a3;</i>
          <i class="text">用户管理</i>
          <i class="arrow-right"> > </i>
        </template>
        <router-link :to="{name: 'UserInfoManage'}"> 
          <el-menu-item index="4-5">
            <i class="item-text">用户基本信息管理</i>
          </el-menu-item>
        </router-link>
        <!-- <router-link :to="{name: 'UserInfo'}">
          <el-menu-item index="4-1">
              <i class="item-text">用户类别管理</i>
          </el-menu-item>
        </router-link> -->
        <router-link :to="{name: 'StudentInfoManage'}">
          <el-menu-item index="4-2">
            <i class="item-text">学生信息管理</i>
          </el-menu-item>
        </router-link>
        <router-link :to="{name: 'TeacherInfoManage'}">
          <el-menu-item index="4-3">
            <i class="item-text">教师信息管理</i>
          </el-menu-item>
        </router-link>
        <router-link :to="{name: 'OtherInfoManage'}">
          <el-menu-item index="4-4">
            <i class="item-text">专家及其他人员信息管理</i>
          </el-menu-item>
        </router-link>
      </el-submenu>
    </el-menu>
  </div>
</template>
<script>
export default {
  data() {
    return {
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>
<style scoped>
i { 
  font-style:normal;
} 
.container {
  background-color: #2a3f54;
  display: inline-block;
  width: 250px;
}
.no-subitem {
  width: 11.69px;
  height: 1px;
  float: right;
}
.el-menu-vertical {
  background-color: #2a3f54;
}
.el-menu--collapse {
  width: 100%;
}
.iconfont {
  font-size: 1.5rem;
  font-weight: normal;
  margin-left: -100px;
}
.text {
  margin-left: 10px;
  color: #9BA4AC;
}
.arrow-right {
  float: right;
  font-size: 20px;
  font-weight: normal;
  color: #9BA4AC;
}
.el-menu-item {
  text-align: left;
  padding: 5px 1px;
  background-color: rgba(43, 63, 84, 0.5);
  height: auto;
}
.item-text {
  padding: 0 8px;
  line-height: 20px;
  color: azure;
}
.router-link-active {
  text-decoration: none;
}
</style>
