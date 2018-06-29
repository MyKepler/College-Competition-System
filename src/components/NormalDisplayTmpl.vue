<template>
  <section class="container">
    <header>
      <div class="title">{{title}}</div>
    </header>
    <section class='content-section'>
      <div v-for="(i,index) in content" :key='index' class="content"  @click="forDetail(i)">
        <div class="item-date">
          <span class="day">{{i.day}}</span>
          <span class="year-month">{{i.yearMonth}}</span>
        </div>
        <div class="item-detail">
          <span class="title">【{{i.title}}】</span>
          <span class="detail">
            {{i.introduction}}
          </span>
        </div>
      </div>
    </section>
  </section>
</template>
<script>
export default {
  props: {
    title: String,
    content: Array
  },
  methods: {
    forDetail(i) {
      this.$router.push(`/FileShow/${i.url}`)
    }
  },
  mounted:function(){

  },
}
</script>
<style lang="css" scoped>
.container {
  cursor: pointer;
  margin: 20px 0;
}
header {
  width: 100%;
  height: 34px;
  margin-bottom: 10px;
  border-bottom: 2px solid #5394C5; 
}
header .title {
  width: 128px;
  height: 100%;
  background: #5394C5;
  color: #fff;
  font-size: 16px;
  text-align: center;
  line-height: 40px;
}

.content {
  height: 82px;
  padding: 8px 20px;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
}

.item-detail,
.item-date {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-content: center;
}

.item-date {
  height: 55px;
  width: 55px;
  margin-right: 30px;
  color: #5394C5;
}

.item-date .day,
.item-date .year-month {
  text-align: center;
}
.item-date .day {
  font-size: 30px;
}
.item-date .year-month,
.item-detail .title {
  font-size: 14px;
}

.item-detail .detail {
  width: 722px;
  height: 30px;
  line-height: 16px;
  font-size: 13px;
  overflow: hidden;
}


</style>
