<template>
    <div class="wrap">  
        <!-- 头部按钮 -->       
        <div class="admin-manage">
            <span style="position:absolute;left:80px">项目管理&nbsp; >&nbsp;项目申请管理</span>
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
            </el-table-column>
            <el-table-column
                label="操作"
                width="340"
                :resizable="false">
                <template scope="scope">
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
                type="primary"
                @click="handlePublic(scope.$index, scope.row)"
                v-html="scope.row.state==='申请' ? '审核通过' : '审核失败'"></el-button>
                <el-button
                size="small"
                type="danger"
                @click="handlePublish(scope.$index, scope.row)"
                v-html="scope.row.release==='下架' ? '发布' : '下架'"></el-button>
                <!-- <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)"
                    v-html="scope.row.state==='不可用' ? '启用' : '禁用'">启用</el-button> -->
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
import {getData} from './../../../../util/util.js'
export default {
    data(){
        return{
            //底部分页效果
            //当前页，每页条数，一共多少条
            //后端加载的时候会变化
            currentPage: 1,
            pageSize: 10,
            totalPage: 100, 
            tableData:[],
            keyFormatMap: {
                // 格式化标签映射表
                title: "项目名称",
                submittime:"提交状态",
                release:"项目通知发布",
                leader: "负责人",
                instructor:"指导老师",
            },
            userId:'',
        }
    },
    mounted:function(){
        this.userId = 'admin';
        this.initProject(this.currentPage,this.pageSize);
    },
    watch:{
        '$route'(to,from){
            this.initProject(this.currentPage,this.pageSize);
            // console.log("当前路径",this.$route.path);
        }
    },
    methods: {
        // 是否发布到前端进行项目成员招募
        handlePublish(index,row){
          var mystate=row.release==="下架"?"发布":"下架";
            let params = {
                proId: row.id,
                publish_user: this.userId,
                //   publish_time: getData(),
                //   end_time: getData(),
                publish_time:'2018-01-01',
                end_time: '2018-01-01',
                state: mystate,
            }
            // console.log(params);
            axios.post('/api/jsp/ReRecState',qs.stringify(params))
            .then(response=>{
                // console.log(response.data.msg);
                if(response.data.state=='200'){
                    // console.log("更新成功");
                    row.release=mystate;
                }
            }).catch(err=>{
                console.log(err);
            })
        },
        initProject(pageNum,pageSize){
            let params = {
                // filter: filter, 筛选
                pageNum: pageNum,
                pageSize: pageSize
            }
            axios.post('/api/jsp/FindAllProject',qs.stringify(params))
            .then(response=>{
                console.log(response);
                this.tableData = [];
                response.data.datas.forEach(element => {
                    this.tableData.push({
                        id:element.proObj.pro_id,
                        title:element.proObj.pro_name,
                        submittime:element.proObj.is_submit,
                        release: element.recObj.state,
                        leader:element.proObj.pro_principal_id,
                        instructor:element.proObj.pro_teacher_id,
                        state:element.proObj.pro_status,
                    })
                });
                // console.log(response.data.datas);
            }).catch(err=>{
                console.log(err);
            });
            axios.post('/api/jsp/Count')
            .then(response=>{
                // this.totalPage = response.data.datas;
                response.data.datas.forEach(element=>{
                    if(element.num_name == "项目申请"){
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
        enterAdd(){
            this.$router.push( `/SignUpInfoAdd`);
            console.log("添加按钮");
        },
        // 查看
        handleMore(index,row){
            this.$router.push( `/SignUpInfo/0/${row.id}`);
        },
        handlePublic(index,row){
            var mystate=row.state==="申请"?"中期":"申请";
            let params = {
                proId:row.id,
                state:mystate
            }
            axios.post('/api/jsp/ReProjectState',qs.stringify(params))
            .then(response=>{
                if(response.data.state=='200'){
                    console.log("更新成功");
                    row.state=mystate;
                }
            }).catch(err=>{
                console.log(err);
            })
        },
        // 编辑
        handleEdit(index,row){
            this.$router.push( `/SignUpInfo/1/${row.id}`);
        },
        handleSizeChange(val) {
            //console.log(`每页 ${val} 条`);
            this.pageSize = val;
            this.initProject(this.currentPage,this.pageSize);
        },
        handleCurrentChange(val) {
            //console.log(`当前页: ${val}`);
            this.currentPage = val;
            this.initProject(this.currentPage,this.pageSize);
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
  background-color: #9b59b6;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #9b59b6;
}
.exit-filter {
  float: right;
  background-color: #f19500;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #f19500;
}
.table {
    margin: 30px 80px;
}
.cell{
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
