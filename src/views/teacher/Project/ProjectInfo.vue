<template>
  <div class="container">
    <h2 class="title">项目信息管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span v-if= "type==='0'">项目信息查看管理</span>
        <span v-if= "type==='1'">项目信息修改管理</span>
      </div>
      <div class="btn-wrap" v-if= "type==='0'">
        <el-button type="primary" size="small" @click="toUpdate">修改信息</el-button>
        <!-- <el-button type="danger" size="small">删除该项目</el-button> -->
      </div>
      <div class="btn-wrap" v-if= "type==='1'">
        <el-button type="primary" size="small" plain @click="cancel">取消修改</el-button>
        <el-button type="warning" size="small" plain @click="affirm">确认修改</el-button>
      </div>
    </div>
    <div class="info-wrapper">
      <div class="message-project">
        <el-row>
          <el-col :span="6">
            <span class="item-name">项目名称</span>
            <div class="item-content">
              <el-input placeholder="请输入项目名称" v-model="project.name" :disabled="isCheck"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">项目阶段</span>
            <div class="item-content">
                <el-select v-model="project.status" :disabled="!isCheck" @change="initReviewMsg(project.status)">
                  <el-option
                    v-for="item in options2"
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
              <el-input placeholder="请输入项目负责人" v-model="project.leader" :disabled="isCheck"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">开始时间</span>
            <div class="item-content">
              <el-date-picker
                :disabled="isCheck"
                v-model="project.startTime"
                type="date"
                :picker-options="pickerOptions1">
              </el-date-picker>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <span class="item-name">截止时间</span>
            <div class="item-content">
              <el-date-picker
                :disabled="isCheck"
                v-model="project.endTime"
                type="date"
                :picker-options="pickerOptions1">
              </el-date-picker>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">指导老师</span>
            <div class="item-content">
              <el-input placeholder="请输入指导老师" v-model="project.counselor" :disabled="isCheck"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">评审人</span>
            <div class="item-content">
              <el-input v-model="project.reviewer" disabled></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">评审分数</span>
            <div class="item-content">
                <el-input v-model="project.result" disabled></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">已报名人数（人）</span>
              <div class="item-content">
                <el-input disabled placeholder="请输入已报名人数" v-model="project.currentNum"></el-input>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">计划报名人数（人）</span>
              <div class="item-content">
                <el-input placeholder="请输入计划报名人数" v-model="project.planNum" :disabled="isCheck"></el-input>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">项目状态</span>
              <div class="item-content">
                <el-input v-model="project.state" disabled></el-input>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="message-members">
        <div class="message-title">
          <span>负责人</span>
        </div>
        <div class="message-wrapper">
          <div class="message-wrapper-item">
            <span>负责人</span>
            <el-button class="btn-check" type="primary" size="small" @click="toStuDetail">详情查看</el-button>
            <div class="itemshow">
              <el-row>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">学号</span>
                    <div class="item-content">
                      <el-input disabled v-model="leader.userId"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">姓名</span>
                    <div class="item-content">
                      <el-input disabled v-model="leader.name"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">邮箱</span>
                    <div class="item-content">
                      <el-input disabled v-model="leader.email"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">手机号</span>
                    <div class="item-content">
                      <el-input disabled v-model="leader.userPhone"></el-input>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
          
        </div>
      </div>
      <div class="message-file">
        <div class="message-title">
          <span>附件</span>
        </div>
        <el-row>
          <el-col :span="16">
            <div class="item-content">
            <el-button type="success" size="mini"  v-if="fileList.length > 0" @click="downloadAll">下载附件</el-button>
            <el-row :gutter="200" class="info-content"  v-if="fileList.length > 0">
              <el-col :span="24" class="info-item">
                <div class="item-content">
                  <div class="attack-link">
                    <ul v-if="fileList.length > 0" class="download">
                      <li v-for="file in fileList" :key="fileList.fileList" @click="downloadOne">{{ file.name }}</li>
                    </ul>
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
          <div class="item-content" v-if="fileList.length == 0">
            暂无文件
          </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
export default {
  data () {
    return {
      options2: [{
        value: '申请',
        label: '申请'
      }, {
        value: '中期',
        label: '中期',
      },{
        value: '结题',
        label: '结题',
      }],
      pickerOptions1: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      project: {
        name: "",
        status: '',
        leader: '',
        leader_id: '',
        startTime: '',
        endTime: '',
        teacher_id: '',
        counselor:'',
        reviewer: '',
        result: '',
        currentNum: '',
        planNum: '',
        state: ''
      },
      leader: {
        userId: '',
        name: "",
        email: "",
        userPhone: ''
      },
      fileList: [],
      type: '0',   //0为查看通知；1为修改通知
      isCheck: true,
      proId: ''
    }
  },
  components: {

  },
  mounted:function(){
    // 获取路由上的type参数
    this.type = this.$route.params.type;
    this.proId = this.$route.params.proId;
    this.initProPersonal();
    this.initFileList();
    if(this.type == '0'){
      this.isCheck = true;
    }else{
      this.isCheck = false;
    }
  },
  methods: {
    //跳转到编辑
    toUpdate(){
      this.type = '1';
      if(this.type == '0'){
        this.isCheck = true;
      }else{
        this.isCheck = false;
      }
      this.$router.push(`/TeacherProjectInfo/1/${this.proId}`);
    },
    //取消修改
    cancel(){
      this.initProPersonal();
    },
    //确认修改
    affirm(){
      let params ={
        proId: this.proId,
        name: this.project.name,
        endTime: this.project.endTime,
        planNum: this.project.planNum,
        startTime: this.project.startTime,
        leader: this.project.leader,
        counselor: this.project.counselor
      }
      axios.post('/api/jsp/UpdateProInfo',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/teacher/TeacherProjectInfoManage`);
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    //初始化
    initProPersonal(){
      let params = {
        proId:this.proId
      }
      // console.log(this.proId);
      axios.post('/api/jsp/FindProInfoById',qs.stringify(params))
      .then(response=>{
        console.log(response.data.datas);
        let proObj = response.data.datas.proObj;
        let stuObj = response.data.datas.leaderObj;
        let teaObj = response.data.datas.teaObj;
        let reviewObj = response.data.datas.reviewObj;

        this.project.name = proObj.pro_name;
        this.project.status = proObj.pro_status;
        this.project.currentNum = proObj.act_num;
        this.project.planNum = proObj.plan_num;
        this.project.startTime = proObj.start_year;
        this.project.endTime = proObj.finish_year;
        this.project.state = proObj.pro_state;

        this.project.leader_id = stuObj.user_id;
        this.project.leader = stuObj.user_name;

        this.project.teacher_id = teaObj.user_id;
        this.project.counselor = teaObj.user_name;

        this.project.reviewer = reviewObj.review_user_name;
        this.project.result = reviewObj.review_code;

        this.leader.userId =  stuObj.user_id;
        this.leader.name = stuObj.user_name;
        this.leader.email = stuObj.user_mail;
        this.leader.userPhone = stuObj.user_phone;


      })
      .catch(err=>{
        console.log(err);
      })
    },
    initFileList(){
      let params = {
         id: this.proId,
      }
      axios.post('/api/jsp/ProjectMangeFileQueryAll',qs.stringify(params))
      .then(response=>{
        let fileData = response.data.datas;
        fileData.forEach(element => {
          this.fileList.push({
            name: element.fileName,
            url: element.filePath,
          })
        });
        console.log(response)
      })
      .catch(err=>{
        console.log(err);
      })
    },
    //通过项目id和阶段来查询对应的评审信息
    initReviewMsg(init){
      let params = {
        proId:this.proId,
        proStatus:init
      }
      axios.post('/api/jsp/FindRevMsgInPro',qs.stringify(params))
      .then(response=>{
        // 评审信息
        let reviewMsg = response.data.datas;
        if(reviewMsg==undefined){
          this.project.reviewer = '';
          this.project.result = '';
        }else{
          this.project.reviewer = reviewMsg.review_user_name;
          this.project.result = reviewMsg.review_code;
        }


      })
      .catch(err=>{
        console.log(err);
      })

    },
    // 查看学生详细信息
    toStuDetail(){
      this.$router.push(`/TeacherCheckStu/${this.leader.userId}`);
    },
     // 下载所有文件
    downloadAll(){
       window.open("");
    },
    // 下载单个文件
    downloadOne(){},
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
.btn-confirm{
  padding: 8px 18px;
}
.info-wrapper{
  margin: auto;
  padding: 20px 3%;
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
.message-members{
  margin: 30px 0 0 0;
}
.message-wrapper-item{
  margin-top: 20px;
}
.message-file{
  margin: 30px 0 0 0;
}
.message-title{
  margin: 15px 0;
  padding: 10px 0;
  font-size: 16px;
  border-bottom: 1px solid gray;
}
.btn-check{
  margin-left: 12px;
}
.itemshow{
  margin-top: 10px;
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
.remark-input{
  width: 92.5%;
}
.check{
  display: none;
}
</style>
