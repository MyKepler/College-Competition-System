<template>
    <div class="wrap">
        <!-- 头部按钮 -->
        <div class="admin-manage">
            <span style="position:absolute;left:80px">项目管理&nbsp; >&nbsp;项目报名审核</span>
            <el-row>
                <el-col :span="2" :offset="20">
                    <el-button class="exit-filter" size="mini" @click="quitFilter">退出筛选</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button class="filter" size="mini" @click="enterFilter">筛选信息</el-button>
                </el-col>
                <!-- <el-col :span="2">
                    <el-button class="addInfo" size="mini" type="success"  @click="enterAdd">添加信息</el-button>
                </el-col> -->
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
            </el-table-column>
            <el-table-column
                label="操作"
                width="300"
                :resizable="false">
                <template scope="scope">
                <el-button
                    size="small"
                    type="primary"
                    @click="handleMore(scope.$index, scope.row)">更多</el-button>
                <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
                    v-html="scope.row.state==='审核成功' ? '审核失败' : '审核成功'"></el-button>
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
    </div>
</template>
<script>
import axios from 'axios'
import qs from 'qs'
export default {
    data(){
        return{
            //底部分页效果
            //当前页，每页条数，一共多少条
            //后端加载的时候会变化
            currentPage: 1,
            pageSize: 10,
            totalPage: 1000,
            tableData:[
                {
                    // id:1,
                    // name:"于玉琪",
                    // title: "标题1",
                    // signtime: "2018-5-29",
                    // teacher: "2018-5-29",
                    // state: "审核成功"
                }
            ],
            keyFormatMap: {
                // 格式化标签映射表
                user_name:"报名者",
                title: "项目名称",
                signtime: "报名时间",
                teacher: "指导老师",
                state: "状态"
            }
        }
    },
    watch:{
        '$route'(to,from){
            this.initProAudit(this.currentPage,this.pageSize);
        }
    },
    mounted:function(){
        this.initProAudit(this.currentPage,this.pageSize);
    },
    methods: {
        initProAudit(pageNum,pageSize){
            let params = {
                // filter: filter, 筛选
                pageNum: pageNum,
                pageSize: pageSize
            }
            axios.post('/api/jsp/FindAllProAudit',qs.stringify(params))
            .then(response=>{
                this.tableData = [];
                console.log(response.data.datas);
                response.data.datas.forEach(element => {
                this.tableData.push({
                    id:element.signUpObj.sign_up_id,
                    proId: element.proObj.pro_id,
                    title:element.proObj.pro_name,
                    user_id:element.signUpObj.user_id,
                    user_name: element.signUpObj.user_name,
                    signtime: element.signUpObj.sign_up_time,
                    teacher: element.teaObj.user_name,
                    state: element.signUpObj.state
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
                    if(element.num_name == "项目报名审核"){
                        this.totalPage = element.num;
                        console.log(this.totalPage);
                    }
                });
            }).catch(err=>{
                console.log(err);
            });
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
        // 查看
        handleMore(index,row){
            this.$router.push( `/ProjectAuditInfo/${row.id}`);
            console.log("更多按钮");
        },
        // 审核按钮
        handleDelete(index,row){
            var mystate=row.state==="审核成功"?"审核失败":"审核成功";
            // row.state=mystate;
            var proId = row.proId;
            var recId = row.id;
            var userId = row.user_id;
            console.log("审核按钮");
            let params = {
              recId: recId,
              state: mystate,
              proId:proId,
              userId: userId,
            }
            console.log(params);
            axios.post('/api/jsp/ReProAuditState',qs.stringify(params))
            .then(response=>{
              console.log(response.data.msg);
              if(response.data.state=='200'){
                  this.initProAudit(this.currentPage,this.pageSize);
                console.log("更改成功");
              }
              this.dialogFormVisible2 = false;
            }).catch(err=>{
                console.log(err);
            });

        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageSize = val;
            this.initProAudit(this.currentPage,this.pageSize);
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
             this.currentPage = val;
            this.initProAudit(this.currentPage,this.pageSize);
        },
    }
}
</script>
<style>
.admin-manage{
    border-bottom: 1px solid #949494;
    height: 70px;
    margin: 0 80px;
    padding-top: 30px;
    font-size: 18px;
}
/* .addInfo {
  float: right;
  margin-right: 10px;
} */
.filter {
  float: right;
  background-color: #9b59b6;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #9b59b6;
}
.exit-filter {
  float: right;
  margin-right: 20px;
  background-color: #f19500;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #f19500;
}
.table{
    margin: 30px 80px;
}
table
  {
  width: 100%;
  border-collapse:separate;
  font-size: 14px;
  background-color: #fff;
  text-align: center;
  }
.cell {
    text-align: center;
}
.page {
    padding-top: 20px;
}
.router-link-active{
  text-decoration: none;
}
</style>
