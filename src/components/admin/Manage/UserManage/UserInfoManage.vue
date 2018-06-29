<template>
 <div class="wrap">
    <!-- 头部按钮 --> 
    <div class="admin-manage">
        <span style="position:absolute;left:80px">用户管理&nbsp; >&nbsp;基本用户信息管理</span>
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
        <!-- 表格table -->
        <!--data绑定数据，stripe斑马纹border边框row-key绑定一行的index-->
        <el-table
        :data="tableData"
        stripe
        border
        :row-key="getRowKeys"
        size="mini"
        style="width: 100%;">
        <!--resizable是否可以拖动 type=index按序号递增-->
        <el-table-column
            type="index"
            width="50"
            :resizable="false">
            {{tableData.id}}
        </el-table-column>
        <el-table-column
            :label="value"
            :prop="key"
            :resizable="false"
            v-for="(value, key) in keyFormatMap"
            :key="key"
            style="text-align:center;">
        </el-table-column>
        <el-table-column
            label="操作"
            width="300"
            :resizable="false">
            <!--scope就是插槽-->
            <template slot-scope="scope">
                <el-button
                    size="small"
                    type="primary"
                    @click="handleMore(scope.$index, scope.row)">更多</el-button>
                <el-button
                    size="small"
                    class="edit-btn"
                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
                    v-html="scope.row.state==='不可用' ? '启用' : '禁用'">启用</el-button>
            </template>
        </el-table-column>
        </el-table>
            
    </el-row>
    <!--分页-->
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
                totalPage: 100,                
                tableData:[
                ],
                keyFormatMap: {
                    // 格式化标签映射表
                    userId: "用户名",
                    name: "姓名",
                    type:"用户类别",
                    state: "用户状态"
                },
            }
        },
        mounted: function() {
            this.initUser(this.currentPage,this.pageSize);
        },
        watch:{
            '$route'(to,from){
                this.initUser(this.currentPage,this.pageSize);
            }
        },
        methods:{
            initUser(pageNum,pageSize){
                let params = {
                    // filter: filter, 筛选
                    pageNum: pageNum,
                    pageSize: pageSize
                }
                axios.post('/api/jsp/FindAllUser',qs.stringify(params))
                .then(response=>{
                    this.tableData = [];
                    response.data.datas.forEach(element => {
                        this.tableData.push({
                            userId:element.user_id,
                            name:element.user_name,
                            type:element.user_identity,
                            state:element.account_state 
                        })
                    });
                }).catch(err=>{
                    console.log(err);
                });
                axios.post('/api/jsp/Count')
                .then(response=>{
                    // this.totalPage = response.data.datas;
                    response.data.datas.forEach(element=>{
                        if(element.num_name == "用户"){
                            this.totalPage = element.num;
                            // console.log(this.totalPage);
                        }
                    });
                }).catch(err=>{
                    console.log(err);
                });
            },
            getRowKeys(row){
                return row.id;
            },
            quitFilter(){
                console.log("退出筛选按钮");
            },
            enterFilter(){
                console.log("筛选按钮");
            },
            enterAdd(){
                console.log("添加按钮");
            },
            handleMore(index,row){
                this.$router.push(`/UserInfo/0/${row.userId}`);
                // this.$router.push({name:'UserInfo',params:{type:0,user_id:row.userId}});
                console.log(row.userId);
            },
            handleEdit(index,row){
                this.$router.push(`/UserInfo/1/${row.userId}`);
                console.log("编辑按钮");
            },
            handleDelete(index,row){
                var mystate=row.state==="不可用"?"可用":"不可用";
                row.state=mystate;
                let params = {
                    userId:row.userId,
                    state:mystate
                }
                axios.post('/api/jsp/ReUserState',qs.stringify(params))
                .then(response=>{
                    if(response.data.state=='200'){
                        console.log("更新成功");
                        row.state=mystate;
                    }
                }).catch(err=>{
                    console.log(err);
                })
            },
            handleSizeChange(val) {
                //console.log(`每页 ${val} 条`);
                this.pageSize = val;
                this.initUser(this.currentPage,this.pageSize);
            },
            handleCurrentChange(val) {
                //console.log(`当前页: ${val}`);
                this.currentPage = val;
                this.initUser(this.currentPage,this.pageSize);
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
