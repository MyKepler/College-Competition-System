<template>
  <div class="container">
    <h2 class="title">学生报名管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span>学生报名管理</span>
      </div>
      <div class="btn-wrap">
        <el-button type="primary" size="small" @click="addNewPer">确认提交</el-button>
      </div>
    </div>
    <div class="info-wrapper">
      <div class="message-project">
        <div class="message-title">
          <span>项目基本信息</span>
        </div>
        <div class="message-wrapper">
          <el-row>
            <el-col :span="6">
              <span class="item-name">项目名称</span>
              <div class="item-content">
                <el-select placeholder="请输入项目名称" v-model="project.name" @change="findProData(project.name)">
                  <el-option
                    v-for="item in projectName"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">项目负责人</span>
              <div class="item-content">
                <el-input v-model="project.leader" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">负责人手机号</span>
              <div class="item-content">
                <el-input v-model="project.leaderPhone" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">指导老师</span>
              <div class="item-content">
                <el-input v-model="project.counselor" disabled></el-input>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <span class="item-name">指导老师手机号</span>
              <div class="item-content">
                <el-input v-model="project.counselorPhone" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">开始时间</span>
              <div class="item-content">
                <el-date-picker  disabled
                  v-model="project.startTime"
                  type="date"
                  value-format="yyyy-MM-dd"
                  :picker-options="pickerOptions1">
                </el-date-picker>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="message-members">
        <div class="message-title">
          <span>报名人信息</span>
        </div>
        <div class="message-wrapper">
          <el-row>
            <el-col :span="6">
              <span class="item-name">学号</span>
              <div class="item-content">
                <el-input placeholder="请输入你的学号" v-model="user.userId"></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">姓名</span>
              <div class="item-content">
                <el-input placeholder="请输入你的姓名" v-model="user.name"></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">邮箱</span>
              <div class="item-content">
                <el-input placeholder="请输入你的邮箱" v-model="user.email"></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">手机号</span>
              <div class="item-content">
                <el-input placeholder="请输入你的手机号" v-model="user.phone"></el-input>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <span class="item-name">报名时间</span>
              <div class="item-content">
                <el-date-picker  disabled
                  v-model="user.recruitTime"
                  type="date"
                  :picker-options="pickerOptions1">
                </el-date-picker>
                </div>
            </el-col>
            <el-col :span="6">
                <span class="item-name">审核状态</span>
                <div class="item-content">
                  <el-input disabled v-model="user.reviewStatus"></el-input>
                </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="message-file">
        <div class="message-title">
          <span>提交材料证明</span>
        </div>
        <div class="message-wrapper">
          <el-row>
            <el-col :span="16">
              <span class="item-name">附件</span>
              <div class="item-content">
                <el-upload
                  ref="upload"
                  action="/api/jsp/StudentSignUpFileUpload"
                  :on-success="handleSuccess"
                  :on-preview="handlePreview"
                  :on-remove="handleRemove"
                  :file-list="fileList"
                  :data="fromData"
                  :auto-upload="false">
                  <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
                  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                  <el-button style="margin-left: 10px;" size="small" type="danger">删除全部</el-button>
                </el-upload>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import {getDate} from './../../../util/util.js'
export default {
  data () {
    return {
      pickerOptions1: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      projectName: [],
      project: {
        name: "",
        status: '',
        leader: '',
        leaderPhone: '',
        counselor:'',
        counselorPhone: '',
        startTime: '',
      },
      user: {
        userId: '',
        name: '',
        email: '',
        phone: '',
        recruitTime: '',
        reviewStatus: '审核失败'
      },
      fileList: [
            // {name: '', url: ''}
      ],
      fromData:{
        id: 'yhcj'
      },
      proId:'',
      isUpload: false,
    }
  },
  components: {

  },
  mounted: function() {
    this.initProTitle();
    this.user.recruitTime = getDate();
  },
  methods: {
    initProTitle(){
      axios.post('/api/jsp/StudentSignUpQueryAllEnableProjects')
      .then(res=>{
        // console.log(res);
        res.data.datas.forEach(element=>{
          this.projectName.push({
            value:element.projectId,
            label:element.projectName,
          })
        });
      })
      .catch(err=>{
          console.log(err);
      });
    },
    findProData(title){
      let params = {
        id: title
      }
      this.proId = title;
      axios.post('/api/jsp/StudentSignUpSelectOneEnableProjects',qs.stringify(params))
      .then(response=>{
        console.log(response);
        let pro = response.data.datas;
        this.project.name = pro.projectName;
        this.project.leader = pro.projectPrincipalName;
        this.project.leaderPhone = pro.projectPrincipalPhoneNumber;
        this.project.counselor = pro.projectTeacherName;
        this.project.counselorPhone = pro.projectTeacherPhoneNumber;
        this.project.startTime = pro.startYear;
      })
      .catch(err=>{
        console.log(err);
      })
    },
    addNewPer(){
      this.getId();
      let params ={
        // 需要和文件搭配
        signUpId: this.fromData.id,
        projectId: this.proId,
        userId: this.user.userId,
        userName: this.user.name,
        email: this.user.email,
        phone: this.user.phone,
        time: this.user.recruitTime,
        state: this.user.reviewStatus,
      }
      axios.post('/api/jsp/StudentSignUpAddNewPerson',qs.stringify(params))
      .then(res=>{
        console.log(res)
        if(res.data == 200){
          this.$router.push(`student/AuditManage`);
        }
      })
      .catch(err=>{
        console.log(err)
      });
      
    },
    // 决定通知的id是前端生成还是后端生成
    getId(){
      if(!this.isUpload){
        this.fromData.id = new Date().getTime();
        // this.notify.id = this.fromData.id;
        console.log(this.fromData.id);
      }
    },
    handleSuccess(res,file,fileList){
      this.fromData.id = res.datas;
      console.log(this.fromData.id)
    },
    submitUpload() {
      this.isUpload = true;
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
  }
}
</script>

<style scoped>
.container{
  width:90%;
  margin: auto;
  padding: 10px 0;
  min-width: 1350px;
  text-align: left;
}
.title{
  text-align: center;
  padding: 0 0 20px 0;
  margin: 0;
  border-bottom: 5px solid #cbcbcb;
}
.head-info-wrapper{
  display: flex;
  align-items:center;
  justify-content: space-between;
  padding: 10px 0;
  font-size: 16px;
}
.info-wrapper{
  margin: auto;
  padding: 10px 3%;
  background-color: #ffffff;
}
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child{
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
.message-members,
.message-file{
  margin: 30px 0 0 0;
}
.message-title{
  margin-bottom: 15px;
  padding: 10px 0;
  font-size: 16px;
  border-bottom: 1px solid gray;
}
.item-name{
  font-size: 16px;
}
.item-content{
  margin-top: 15px;
}
.el-input,.el-select,.el-date-picker{
  width:85%;
}
</style>
