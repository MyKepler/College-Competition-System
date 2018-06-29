<template>
    <footer>
      <div class="friend-links">
        <div class="friend-content">
          <el-row>
            <el-col :span="10">
                <el-row style="color:#5394C5;">常用链接</el-row>
                <hr style="color:#CCCCCC">
                <el-row>
                    <el-col :span="8">师大校友 ></el-col>
                    <el-col :span="8">师大校友 ></el-col>
                    <el-col :span="8">师大校友 ></el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">师大校友 ></el-col>
                    <el-col :span="8">师大校友 ></el-col>
                    <el-col :span="8">师大校友 ></el-col>
                </el-row>
            </el-col>
            <el-col :span="12" :offset="2">
                <img src="../assets/pic.jpg" style="width:25%;margin-left:45px;">
                <img src="../assets/pic.jpg" style="width:25%;margin-left:15px;">
                <img src="../assets/pic.jpg" style="width:25%;margin-left:15px;">
            </el-col>
        </el-row>
        </div>
      </div>
      <div class="meta-container">
        <div class="meta-container-content">
          <el-row>
            <el-col :span="6">联系电话 ：28865012</el-col>
            <el-col :span="7">本科招生热线 : 0571-28865193 </el-col>
            <el-col :span="7">研究生招生热线 ：0571-28865143</el-col>
            <el-col :span="4">浙ICP备11056902号</el-col>
          </el-row>
          <el-row>
            <el-col :span="6">版权所有杭州师范大学</el-col>
            <el-col :span="9">地址：浙江省杭州市余杭区仓前街道余杭塘路2318号</el-col>
          </el-row>
        </div>
      </div>
    </footer>
</template>


<style scoped>
  .friend-links {
    background-color: #F8F7F4;
    border-bottom: 1px solid #fff;
    padding: 20px 20px;
    width:100%;
  }
  .friend-content {
    width: 1000px;
    height: auto;
    margin: 0 auto;
    text-align:left;
    font-size:15px;
    line-height: 35px;
  }
  .meta-container {
    background:#5394C5;
    padding: 20px 20px;
    line-height: 30px;
    width:100%;
  }
  .meta-container-content {
    width: 1000px;
    height: auto;
    margin: 0 auto;
    text-align:left;
    font-size: 13px;
    color: #fff;
  }
</style>
