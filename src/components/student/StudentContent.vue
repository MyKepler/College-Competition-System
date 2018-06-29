<template>
  <div class="container">
    <keep-alive>
      <router-view></router-view>
    </keep-alive>
  </div>
</template>
<script>
    export default {
      data() {
        return {

        }
      },
      // mounted() {
      //   if (!this.$store.isLogin) {
      //     this.$store.commit("clearStateLogin");
      //     const token = window.localStorage.getItem("userId");
      //     const user = JSON.parse(window.localStorage.getItem("user"));
      //     if (user) {
      //       this.$store.commit("login", { token: token, user: user });
      //       this.loginUser = user.user_name;
      //       console.log(user.user_identity);
      //       if(user.user_identity != "学生"){
      //         this.$router.push('/index')
      //       }
      //     }else{
      //       if (this.$route.path !== '/' || this.$route.path !== '/index') {
      //         this.$router.push('/index')
      //       }
      //     }
      //   }
      // },
    }
</script>
<style scoped>
  .container {
    display: inline-block;
    width: calc(100% - 250px);
    background-color: #ECF0F1;
    height: 100%;
    float: right;
    position: relative;
  }
  .breadcrumb {
    position: absolute;
    font-size: .8rem;
    font-weight: bold;
    color: #2A3F54;
    margin-left: 50px;
    margin-top: 50px;
    display: inline-block;
  }
  .breadcrumb .iconfont {
    font-size: 1.5rem;
  }
</style>