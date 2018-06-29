<template>
  <div class="container">
    <div class="header">
       <admin-head></admin-head>
    </div>
    <div class="wrap">
        <vertical-menu ref="left"></vertical-menu>
        <AdminContent ref="right"></AdminContent>
    </div>
  </div>
</template>
<script>
import AdminHead from "@/components/AdminHeader";
import VerticalMenu from "@/components/admin/VerticalMenu";
import AdminContent from "@/components/admin/AdminContent";

export default {
  components: {
    VerticalMenu,
    AdminHead,
    AdminContent
  },
  methods: {
    changeHeight() {
      const left = this.$refs.left
      const right = this.$refs.right
      console.log(right.getBoundingClientRect)
    }
  }
};
</script>
<style scoped>
.container {
  min-height: 100%;
}
.header {
  min-width: 1200px;
  width: 100%;
}
.wrap {
  position: absolute;
  width: 100%;
  min-width: 1200px;
  top: 80px;
  bottom: 0;
}
.router-link-active {
  text-decoration: none;
}
a {
  text-decoration: none;
}
</style>