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
                学<span class="fill-text">填充</span>号：{{info.user_id}}
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
          <span class="info-item">学<span class="fill-text">填充</span>院：{{info.student_academy}}</span>
          <span class="info-item">专<span class="fill-text">填充</span>业：{{info.student_major}}</span>
        </div>
      </el-col>

      <el-col :span="5" :offset="3">
        <div class="detail-info-wrapper">
          <span class="info-item">班<span class="fill-text">填充</span>级：{{info.student_class}}</span>
          <span class="info-item">用户状态：{{info.account_state}}</span>
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
                
                student_academy:"",
                student_major:"",
                student_class:"",
                account_state:""                    
            }
        }
    },
    mounted:function(){
      this.info.user_id = this.$store.state.user.user_id;
      console.log(this.info.user_id);
      this.initStudentMsg();
    },
    methods: {
      initStudentMsg(){
        let params = {
          userId: this.info.user_id,
        }
        console.log(params);
        axios.post('/api/jsp/FindSpeStu',qs.stringify(params))
          .then(response=>{
          console.log(response.data.datas);
          let stuObj = response.data.datas;
          this.info.user_id = stuObj.user_id;
          this.info.user_name = stuObj.user_name;
          this.info.user_mail = stuObj.user_mail;
          this.info.user_phone = stuObj.user_phone;
          this.info.user_sex = stuObj.user_sex;
          this.info.student_academy = stuObj.student_academy;
          this.info.student_major = stuObj.student_major;
          this.info.student_class = stuObj.student_class;
          this.info.account_state = stuObj.account_state;
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
