<template>
    <div id="app">
        <router-view />
    </div>
</template>

<script>
    export default {
        name: 'App',
    };
</script>
<style lang="scss">
    #app {
        font-family: 'SHS-Normal', Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }
    ::-webkit-scrollbar {
        /*滚动条整体样式*/
        width: 10px; /*高宽分别对应横竖滚动条的尺寸*/
        height: 10px;
    }
    ::-webkit-scrollbar-thumb {
        /*滚动条里面小方块*/
        border-radius: 5px;
        background: #c4d8e1;
    }
    ::-webkit-scrollbar-track {
        /*滚动条里面轨道*/
        border-radius: 0;
    }
</style>
