<template>
  <div class="container">
    <h2 class="title">项目通知管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目通知管理 > </span>
        <span class="add">添加通知</span>
      </div>
      <div class="btn-wrap">
        <el-button type="warning" plain size="small" @click="confrim">确认添加</el-button>
      </div>
    </div>
    <div class="info-wrapper">
      <el-row>
        <el-col :span="8">
          <span class="item-name">标题</span>
          <div class="item-content">
            <el-input placeholder="请输入标题" v-model.trim="$v.notify.title.$model"></el-input>
            <div class="error" v-if="!$v.notify.title.required">Field is required.</div>
          </div>
        </el-col>
        <el-col :span="8">
          <span class="item-name">状态</span>
          <div class="item-content">
            <el-select v-model="notify.status">
              <el-option
                v-for="item in options2"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :span="8">
          <span class="item-name">发布时间</span>
          <div class="item-content">
            <el-date-picker
              v-model="notify.time"
              type="date"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOptions1">
            </el-date-picker>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="item-name">发布者</span>
          <div class="item-content">
            <el-input placeholder="请输入发布者" v-model.trim="$v.notify.publishName.$model"></el-input>
            <div class="error" v-if="!$v.notify.publishName.required">Field is required.</div>
          </div>
        </el-col>
      </el-row>
     <el-row>
        <el-col :span="24">
          <span class="item-name">发布内容介绍</span>
          <div class="item-content">
            <quill-editor v-model="notify.introduce"></quill-editor>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="16">
          <span class="item-name">附件</span>
          <div class="item-content">
            <el-upload
              ref="upload"
              action="/api/jsp/NotifyMangeUpload"
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
</template>

<script>
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import { quillEditor } from 'vue-quill-editor'
import axios from 'axios'
import qs from 'qs'
import { required } from 'vuelidate/lib/validators'
export default {
  components: {
    quillEditor,
  }, 
  data () {
    return {
        options2: [{
            value: '发布',
            label: '发布'
        }, {
            value: '未发布',
            label: '未发布',
        }],
        pickerOptions1: {
            disabledDate(time) {
                // return time.getTime() > Date.now();
            }
        },
        notify: {
            id: '',
            title: "",
            status: '未发布',
            time: '2018-01-01',
            publishName:'',
            introduce: '',
        },
        fileList: [
            // {name: '', url: ''}
        ],
        type: '1',   //1为添加通知管理；2为修改通知；3为查看通知
        fromData:{
          id: 'yhcj'
        },
        isUpload: false,
    }
  },
  validations: {
    notify:{
      title: {
        required,
      },
      publishName: {
        required,
      }
    }
  },
  mounted: function() {
  },
  methods: {
    // 决定通知的id是前端生成还是后端生成
    getId(){
      if(!this.isUpload){
        this.fromData.id = new Date().getTime();
        this.notify.id = this.fromData.id;
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
    // 确认添加
    confrim(){
      this.getId();
      let params = {
        id: this.fromData.id,
        title: this.notify.title,
        status: this.notify.status,
        time: this.notify.time,
        publishName:this.notify.publishName,
        introduce: this.notify.introduce,
      }
      console.log(params);
      if(this.notify.title.length>0 && this.notify.publishName.length>0){
        axios.post('/api/jsp/NotifyMangeAdd',qs.stringify(params))
        .then(res=>{
          if(res.data.state == 200){
             this.$router.push(`/admin/Notification`);
          }
          console.log(res);
        })
        .catch(err=>{
            console.log(err);
        });
      }else{
        console.log("字段为空不能发布")
      }
    }
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
  padding: 20px 3%;
  background-color: #ffffff;
}
.el-row{
    margin-bottom: 40px;
}
.el-row:last-child{
  margin-bottom: 0;
}
.el-col{
  border-radius: 4px;
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
.quill-editor{
  width: 95%;
}

.error {
  display: block;
  color: #f57f6c;
}
</style>
