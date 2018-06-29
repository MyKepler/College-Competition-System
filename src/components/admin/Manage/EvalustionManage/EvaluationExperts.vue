<template>
    <div class="wrap">
        <!-- 头部按钮 -->
        <div class="admin-manage">
            <span style="position:absolute;left:80px">项目管理&nbsp; >&nbsp;项目评审管理</span>
            <el-row>
                <el-col :span="2" :offset="18">
                    <el-button class="exit-filter" size="mini" @click="quitFilter">退出筛选</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button class="filter" size="mini" @click="enterFilter">筛选信息</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button class="addInfo" type="success" size="mini" @click="enterAdd">添加信息</el-button>
                </el-col>
            </el-row>
        </div>
        <!-- 表格table -->
        <el-row class="table">
            <el-table
            :data="tableData"
            stripe
            border
            :row-key="getRowKeys"
            size="mini"
            style="width: 100%;">
            <el-table-column
                type="index"
                width="50"
                :resizable="false">{{tableData.id}}
            </el-table-column>
            <el-table-column
                :label="value"
                :prop="key"
                :resizable="false"
                v-for="(value, key) in keyFormatMap"
                :key='key'
                style="text-align:center;">
                <template slot-scope="scope" >
                <el-select v-model="scope.row.evaluate"
                            v-if="key === 'evaluate'"
                            size="mini"
                            @change="initReviewMsg(scope.$index, scope.row)">
                    <el-option v-for="item in valueLabelMap.evaluate"
                    style="font-size: 14px;"
                    :value="item.value"
                    :label="item.label"
                    :key="item.value">
                    </el-option>
                </el-select>
                <!--如果prop绑定的key！=state则直接现实当前row的key也就是el-table-column中label中的内容-->
                <span v-else>{{scope.row[key]}}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="操作"
                width="240"
                :resizable="false">
                <template scope="scope">
                <el-button
                    size="small"
                    type="primary"
                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <!-- <el-button
                    size="small"
                    class="edit-btn"
                    @click="handleEdit(scope.$index, scope.row)">修改</el-button> -->
                <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
            </el-table>
        </el-row>
        <!-- 分页 -->
        <el-row class="page">
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            background
            layout="prev, pager, next, jumper"
            :total="totalPage">
            </el-pagination>
        </el-row>
         <!--添加评审人-->
        <el-dialog :visible.sync="dialogFormVisible" width="30%" center>
            <el-form :model="form" >
                <el-form-item label="项目名称">
                    <el-select  v-model="addTitle" :disabled="!isAddPro"
                    placeholder="请选择项目" style="width:278px;">
                        <el-option
                        v-for="item in valueLabelMap.proSelect"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="评审阶段" >
                    <el-select v-model="addValue" placeholder="请选择" style="width:278px;">
                        <el-option
                        v-for="item in valueLabelMap.evaluate"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="评审人Id">
                    <el-input  placeholder="请输入评审人ID（以该项为准）" v-model="addUserId"
                     auto-complete="off" style="width:280px;"></el-input>
                </el-form-item>
                <el-form-item label="评审人">&nbsp;&nbsp;
                    <el-input  placeholder="请输入评审人" v-model="addUserName"
                    auto-complete="off" style="width:280px;"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" style="margin-top:-30px;">
                <el-button type="primary" @click="isAddNew">确 定</el-button>
                <el-button @click="dialogFormVisible = false">取 消</el-button>
            </div>
        </el-dialog>
        <!--删除评审人-->
        <el-dialog :visible.sync="dialogFormVisible2" width="30%" center>
            <el-form :model="form" >
                <el-form-item label="项目名称">
                    <el-input  auto-complete="off" style="width:280px;" v-model="delTitle" :disabled="!isAddPro" ></el-input>
                </el-form-item>
                <el-form-item label="评审阶段" >
                    <el-select v-model="delValue" placeholder="请选择删除阶段" style="width:278px;">
                        <el-option
                        v-for="item in valueLabelMap.evaluate"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" style="margin-top:-30px;">
                <el-button type="primary" @click="delProReviewer">确 定</el-button>
                <el-button @click="dialogFormVisible2 = false">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import axios from 'axios'
import qs from 'qs'
export default {
    data(){
        return{
            dialogFormVisible: false,
            dialogFormVisible2: false,
            isAddPro: true,
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            },
            addValue: '',
            delValue: '',
            //底部分页效果
            //当前页，每页条数，一共多少条
            //后端加载的时候会变化
            currentPage: 1,
            pageSize: 10,
            totalPage: 1000,
            tableData:[
                {
                    // title: "这是标题",
                    // captain: "孔志鹏",
                    // evaluate: "申请",
                    // judgerid:"4455",
                    // judger:"于玉琪"
                }
            ],
            valueLabelMap: {
            evaluate: [
                {
                value: "申请",
                label: "申请"
                },
                {
                value: "中期",
                label: "中期"
                },
                {
                value: "结题",
                label: "结题"
                }
            ],
            proSelect:[],
            },
            keyFormatMap: {
            // 格式化标签映射表
            title: "项目名称",
            captain: "项目负责人",
            evaluate:"项目评审阶段",
            judgerid:"评审人ID",
            judger:"评审人"
            },
            proId: '',
            addTitle: '',
            addUserId: '',
            addUserName: '',
            delTitle: '',
        }
    },
    mounted:function(){
        this.initProjecReviewt(this.currentPage,this.pageSize);
        this.initAllProject();
    },
    watch:{
        '$route'(to,from){
            this.initProjecReviewt(this.currentPage,this.pageSize);
        }
    },
    methods: {
      initAllProject(){
        axios.get('/api/jsp/FindAllPro')
        .then(response=>{
            // console.log(response.data.datas)
            response.data.datas.forEach(element => {
            this.valueLabelMap.proSelect.push({
                value:element.pro_id,
                label:element.pro_name,
            })
            });
            // console.log(this.teacher_degree);
        }).catch(err=>{
            console.log(err);
        });
        },
      initProjecReviewt(pageNum,pageSize){
        let params = {
            // filter: filter, 筛选
            pageNum: pageNum,
            pageSize: pageSize
        }
        axios.post('/api/jsp/FindAllProReviewer',qs.stringify(params))
        .then(response=>{
          this.tableData = [];
          console.log(response.data.datas);
          response.data.datas.forEach(element => {
            this.tableData.push({
                id:element.proObj.pro_id,
                title:element.proObj.pro_name,
                evaluate:element.proObj.pro_status,
                captain:element.leaderObj.user_name,
                // teacher_name:element.teaObj.user_name,
                judgerid: element.reviewObj.review_user_id,
                judger: element.reviewObj.review_user_name,
            });
          });
            // console.log(response.data.datas);
        }).catch(err=>{
            console.log(err);
        });

        axios.post('/api/jsp/Count')
        .then(response=>{
            // this.totalPage = response.data.datas;
            response.data.datas.forEach(element=>{
                if(element.num_name == "评审专家管理"){
                    this.totalPage = element.num;
                    // console.log(this.totalPage);
                }
            });
        }).catch(err=>{
            console.log(err);
        });
      },
      //通过项目id和阶段来查询对应的评审信息
      initReviewMsg(index,row){
        let params = {
          proId:row.id,
          proStatus:row.evaluate,
        }
        console.log(params);
        // console.log(this.project.status);
        axios.post('/api/jsp/FindRevMsgInPro',qs.stringify(params))
        .then(response=>{
          console.log(response.data.datas);
          // 评审信息
          let reviewMsg = response.data.datas;
          if(reviewMsg == undefined){
            row.judgerid = '';
            row.judger = '';
          }
          else{
            row.judgerid = reviewMsg.review_user_id;
            row.judger = reviewMsg.review_user_name;
          }
          
        })
        .catch(err=>{
          console.log(err);
        })
      },
      getRowKeys(row) {
          return row.id;
      },
      quitFilter(){
          console.log("退出筛选按钮");
      },
      enterFilter(){
          console.log("筛选按钮");
      },
      enterAdd(){
          this.dialogFormVisible = true;
          this.isAddPro = true;
          console.log("添加项目按钮");
      },
      handleEdit(index,row){
          this.dialogFormVisible = true;
          this.isAddPro = false;
          this.addTitle = row.title;
          this.proId = row.id;
      },
      handleDelete(index,row){
        this.dialogFormVisible2 = true;
          this.isAddPro = false;
          this.delTitle = row.title;
          this.proId = row.id;
          // console.log("删除按钮");
      },
      handleSizeChange(val) {
          console.log(`每页 ${val} 条`);
          this.pageSize = val;
          this.initProjecReviewt(this.currentPage,this.pageSize);
      },
      handleCurrentChange(val) {
          console.log(`当前页: ${val}`);
          this.currentPage = val;
          this.initProjecReviewt(this.currentPage,this.pageSize);
      },
      addProReviewer(){
          let params = {
            proId:this.proId,
            review_type: this.addValue,
            userId:this.addUserId,
          }
          // console.log(this.addTitle);
          axios.post('/api/jsp/ProReviewManage',qs.stringify(params))
          .then(response=>{
            // console.log(response.data.msg);
            if(response.data.state=='200'){
              // this.$router.push(`/admin/ProjectInfoManage`);

                this.initProjecReviewt(this.currentPage,this.pageSize);
              console.log("修改成功");
            }
            this.dialogFormVisible = false;
          }).catch(err=>{
              console.log(err);
          });
      },
      addPro(){
        let params = {
          proId:this.addTitle,
          review_type: this.addValue,
          userId:this.addUserId,
        }
        console.log(this.addTitle);
        axios.post('/api/jsp/ProReviewManage',qs.stringify(params))
        .then(response=>{
          // console.log(response.data.msg);
          if(response.data.state=='200'){
            // this.$router.push(`/admin/ProjectInfoManage`);

              this.initProjecReviewt(this.currentPage,this.pageSize);
            console.log("修改成功");
          }
          this.dialogFormVisible = false;
        }).catch(err=>{
            console.log(err);
        });
      },
      isAddNew(){
        var isAdd = this.isAddPro;
        console.log(isAdd);
        if(isAdd == true){
          this.addPro();
        }else{
          this.addProReviewer();
        }
      },
      delProReviewer(){
        let params = {
            proId:this.proId,
            review_type: this.delValue,
          }
          console.log(params);
          axios.post('/api/jsp/DeleteProReviewer',qs.stringify(params))
          .then(response=>{
            console.log(response.data.msg);
            if(response.data.state=='200'){
              // this.$router.push(`/admin/ProjectInfoManage`);
                this.initProjecReviewt(this.currentPage,this.pageSize);
              console.log("删除成功");
            }
            this.dialogFormVisible2 = false;
          }).catch(err=>{
              console.log(err);
          });
      },
    }
}
</script>
<style>
.admin-manage {
    border-bottom: 1px solid #949494;
    height: 70px;
    margin: 0 80px;
    padding-top: 30px;
    font-size: 18px;
}
.addInfo {
  float: right;
}
.filter {
  float: right;
  margin-right: 20px;
  background-color: #9b59b6;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #9b59b6;
}
.exit-filter {
  float: right;
  margin-right: 40px;
  background-color: #f19500;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #f19500;
}
.table {
    margin: 30px 80px;
}
.cell {
    text-align: center;
}
.page {
    padding-top: 20px;
}
.edit-btn {
  background-color: #5cb85c;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #5cb85c;
}

</style>
