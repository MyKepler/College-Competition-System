<template>
  <div class="wrapper">
    <el-row class="info-wrapper">
            <el-col :span="4" class="avatar-wrapper">
              <img src="@/assets/pic.jpg" alt="用户头像" class="avatar">
            </el-col>
            <el-col :span="4" :offset="1">
              <div class="operate-wrapper">
                <span class="name">姓名：{{info.user_name}}</span>
                <!-- <span class="edit-link" @click="$router.push({name: 'Login'})">修改个人信息></span> -->
                <span class="edit-link" @click="toModify">修改个人信息></span>
              </div>
            </el-col>

            <el-col :span="5" :offset="3">
              <div class="base-info-wrapper">
              <span class="info-item">
                工<span class="fill-text">填充</span>号：{{info.user_id}}
              </span>
                <span class="info-item">
                邮<span class="fill-text">填充</span>箱：{{info.user_mail}}
              </span>
                <span class="info-item">
                手机号码：{{info.user_phone}}
              </span>
              </div>
            </el-col>
    </el-row>
    <el-row class="info-wrapper">
      <el-col :span="4" :offset="5">
        <div class="detail-info-wrapper">
          <span class="info-item">性<span class="fill-text">填充</span>别：{{info.user_sex}}</span>
          <span class="info-item">学<span class="fill-text">填充</span>历：{{info.teacher_bachelor}}</span>
          <span class="info-item">学<span class="fill-text">填充</span>位：{{info.teacher_degree}}</span>
          <span class="info-item">专<span class="fill-text">填充</span>业：{{info.teacher_major}}</span>
        </div>
      </el-col>

      <el-col :span="5" :offset="3">
        <div class="detail-info-wrapper">
          <span class="info-item">公司名称：{{info.company_name}}</span>
          <span class="info-item">公司电话：{{info.company_phone}}</span>
          <span class="info-item">负责人员：{{info.company_principal}}</span>
          <span class="info-item">企业地址：{{info.company_address}}</span>
        </div>
      </el-col>
    </el-row>
  </div>     
</template>
<script>
import axios from 'axios'
import qs from 'qs'
  export default {
    components: {
    },
    data(){
        return{
            info: {
                user_name:"",
                user_id:"",
                user_mail:"",
                user_phone:"",
                user_sex:"",
                
                teacher_degree:"",
                teacher_bachelor:"",
                teacher_major:"",
                company_name:"",
                company_phone:"",
                company_principal:"",
                company_address:"",             
            }
        }
    },
    mounted:function(){
      // 获取路由上的type参数
      // let user =  JSON.parse(window.localStorage.getItem('user')) || this.$store.state.user
      //   console.log('u', user)
      //   this.type = user.type
      this.info.user_id = '201506';
      this.initReviewMsg();
    },
    methods: {
      initReviewMsg(){
        let params={
          userId: this.info.user_id,
        }
        console.log(params);
        axios.post('/api/jsp/FindReviewerById',qs.stringify(params))
        .then(response=>{
          console.log(response.data.datas);
          let reviewInfo = response.data.datas;
          this.info.user_name = reviewInfo.user_name;
          this.info.user_id = reviewInfo.user_id;
          this.info.user_mail = reviewInfo.user_mail;
          this.info.user_phone = reviewInfo.user_phone;
          this.info.user_sex = reviewInfo.user_sex;
          this.info.teacher_degree = reviewInfo.teacher_degree;
          this.info.teacher_bachelor = reviewInfo.teacher_bachelor;
          this.info.teacher_major = reviewInfo.teacher_major;
          this.info.company_name = reviewInfo.company_name;
          this.info.company_phone = reviewInfo.company_phone;
          this.info.company_principal = reviewInfo.company_principal;
          this.info.company_address = reviewInfo.company_address;
        })
        .catch(err=>{
          console.log(err);
        })
      },
      //跳转到修改界面
      toModify(){
        this.$router.push(`/ChangeInfo/${this.info.user_id}`);
      }
    }
  }
</script>
<style>
.wrapper {
  width: 85%;
  margin: 40px auto;
  padding: 4rem;
  border-radius: 1rem;
  background-color: #e4e4e4;
}
.avatar-wrapper{
    margin-right: 10px;
}
.info-wrapper:first-child {
  padding-bottom: 3rem;
  border-bottom: 1px dashed #000;
  margin-bottom: 3rem;
}
.avatar {
  padding: 0.3rem;
  border-radius: 50%;
  border: 2px dashed #ccc;
  height: 160px;
}

.detail-info-wrapper,
.base-info-wrapper,
.operate-wrapper {
  padding: 40px 0;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: left;
}

.base-info-wrapper,
.operate-wrapper {
  height: 100px;
}

.detail-info-wrapper {
  height: 120px;
}

.name {
  font-size: 1.3rem;
}

.edit-link {
  margin-top: 3rem;
  font-size: 0.8rem;
  color: #4586ff;
  cursor: pointer;
}

.fill-text {
  color: transparent;
}
.info-item{
    text-align: left;

}
</style>
