<template>
  <div class="container">
    <el-row class="manage-head">
      <el-col :span="1" >
        <p class="head">项目申请与管理系统</p>
      </el-col>
      <el-col :span="1" :offset="16">
        <img src="@/assets/admin.png"/>
      </el-col>
      <el-col :span="2">
        <p>{{loginUser}}，您好</p>
        <!-- <div>管理员，您好</div> -->
      </el-col>
      <el-col :span="2" >
        <div @click="goIndex">回到首页</div>
      </el-col>
      <el-col :span="2" >
        <div @click="logout">退出登录</div>
      </el-col>
    </el-row>
  </div> 
</template>
<script>
export default {
  data() {
    return {
    };
  },
  methods:{
    goIndex() {
      this.$router.push('/index');
    },
    logout() {
      this.$store.commit('logout');
      this.goIndex();
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
          console.log(user.user_identity);
          if(user.user_identity == "管理员"){
            
          }else if(user.user_identity == "教师"){

          }else if(user.user_identity == "学生"){

          }else if(user.user_identity == "评审专家"){

          }
        }else{
          if (this.$route.path !== '/' || this.$route.path !== '/index') {
            this.$router.push('/index')
          }
        }
      }
  },
  computed: {
    loginUser: function () {
      //如果不是name就是username
      return this.$store.state.user.user_name;
    },
  },
};
</script>
<style scoped>
.container {
  width: 100%;
}
.manage-head {
  height: 80px;
  background-color: #fff;
}
.head {
  width: 250px;
  color: #9BA4AC;
  font-size: 18px;
  background-color: #00284D;
  vertical-align: center;
  line-height: 80px;
}
.manage {
  width: 800px;
}
.manage-head div {
  display: inline-block;
  height: 60px;
  line-height: 70px;
  cursor: pointer;
}
.manage-head img {
  height: 50px;
  margin-top: 10px;
}
</style>
