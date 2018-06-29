<template>
  <div class="container">
    <!--default-active 默认激活菜单的index collapse 是否水平折叠收起菜单-->
    <el-menu default-active="1" class="el-menu-vertical" @open="handleOpen" @close="handleClose" :collapse="true">  
      <!--no-subitem表示没有子菜单-->
      <router-link :to="{name: 'ReviewMyInfo'}"> 
        <el-submenu index="1">
            <template slot="title" class="no-subitem">
                <i class="iconfont">&#xe710;</i>
                <i class="text">个人中心</i>
                <i class="no-subitem"></i>
            </template>
        </el-submenu>
      </router-link>
      <router-link :to="{name: 'ReviewTask'}"> 
        <el-submenu index="2">
          <template slot="title"  class="no-subitem">
            <i class="iconfont">&#xe621;</i>
            <i class="text">任务查看</i>
            <i class="no-subitem"></i>
          </template>
        </el-submenu>
      </router-link>
    </el-menu>
  </div>
</template>
<script>
export default {
  data() {
    return {
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>
<style scoped>
i { 
  font-style:normal;
} 
.container {
  background-color: #2a3f54;
  display: inline-block;
  width: 250px;
}
.no-subitem {
  width: 11.69px;
  height: 1px;
  float: right;
}
.el-menu-vertical {
  background-color: #2a3f54;
}
.el-menu--collapse {
  width: 100%;
}
.iconfont {
  font-size: 1.5rem;
  font-weight: normal;
  margin-left: -100px;
}
.text {
  margin-left: 10px;
  color: #9BA4AC;
}
.arrow-right {
  float: right;
  font-size: 20px;
  font-weight: normal;
  color: #9BA4AC;
}
.el-menu-item {
  text-align: left;
  padding: 5px 1px;
  background-color: rgba(43, 63, 84, 0.5);
  height: auto;
}
.item-text {
  padding: 0 8px;
  line-height: 20px;
  color: azure;
}
.router-link-active {
  text-decoration: none;
}
</style>
