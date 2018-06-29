<template>
  <div class="header-wrapper">
    <header class="header">
      <div class="container">
        <el-row>
          <el-col :span="10">
            <router-link to='/'>
              <div class="grid-content bg-purple logo-wrapper">
                <img class="logo" src="../assets/logoTitle.png" alt="HZNU">
              </div>
            </router-link>
          </el-col>       
          <el-col :span="10" style="height:1px;"></el-col>   
          <el-col :span="4" class="logAndReg" v-if="!isLogin">
            <img src="../assets/person.png" style="width:20%;margin-bottom:-10px;">
            <router-link to="/login">登录</router-link>
            <span class="separate">|</span>
            <router-link to="/register">注册</router-link>
          </el-col>
          <el-col :span="4" class="logAndReg header-menu" v-if="isLogin">
            <div class="more">
            <el-menu  mode="horizontal">
              <el-submenu index="1">
                <template slot="title">您好</template>
                <el-menu-item index="1-1" @click="managePage">管理页面</el-menu-item>
                <el-menu-item index="1-2" @click="logout">退出登录</el-menu-item>
              </el-submenu>
            </el-menu>
          </div>
          </el-col>
        </el-row>
      </div>
    </header>
  </div>
</template>
<script>
  export default {
    data(){
      return {
        
      }
    },
    mounted() {
      if (!this.$store.isLogin) {
        this.$store.commit("clearStateLogin");
        const token = window.localStorage.getItem("userId");
        const user = JSON.parse(window.localStorage.getItem("user"));
        if (user) {
          this.$store.commit("login", { token: token, user: user });
          this.loginUser = user.user_name;
        }
      }
    },
    computed: {
      isLogin: function () {
        return this.$store.state.isLogin;
      },
      // loginUser: function () {
      //   return this.$store.state.user.user_name;
      // },
    },
    methods:{
      logout() {
        this.$store.commit('logout')
      },
      managePage() {
        let router = null;
        console.log(this.$store.state.user)
        switch(this.$store.state.user.user_identity) {
          case '管理员':
            router='/admin'
            break;
          case '学生':
            router='/student'
            break;
          case '教师':
            router='/teacher'
            break;
          case '评审专家':
            router='/review'
            break;
        }
        if (router) {
          this.$router.push(router)
        }
      }
    },
  }
</script>


<style scoped>
.more {
  float: right;
  margin-right: 10px;
}
a {
  color: #000;
}

.header-wrapper {
  height: 100px;
}

.header {
  height: 80px;
  color: #fff;
  background-color: #fff;
  margin-top: -60px;
  /*background-color: #ebecee;*/
}

.header .container {
  height: auto;
  width: 1000px;
  line-height: 80px;
  margin: 0 auto;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}

.logo-wrapper {
  display: flex;
  align-items: center;
  height: 80px;
}

img.logo {
  width: 75%;
}

.el-row {
  border-bottom: 1px solid #c5ceda;
}

.el-col.logAndReg {
  height: 80px;
  padding-top: 9px;
  color: #333;
  text-align: center;
}

.nav-item {
  cursor: pointer;
  color: #000;
  text-align: center;
}
</style>
