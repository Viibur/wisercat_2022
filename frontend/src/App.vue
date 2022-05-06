<template>
  <Filter />
</template>

<script>
import Filter from './components/Filter.vue';

export default {
  name: 'App',
  components: {
    Filter,
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
