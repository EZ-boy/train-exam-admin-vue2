<template>
    <div class="main_page">
        <ts-header></ts-header>
        <div class="main_con">
            <router-view />
        </div>
    </div>
</template>

<script>
    import tsHeader from './tsHeader.vue';
    export default {
        data() {
            return {};
        },
        components: {
            tsHeader,
        },
    };
</script>

<style lang="scss">
    * {
        box-sizing: border-box;
    }
    .main_page {
        width: 100%;
        height: 100%;
    }
    .main_con {
        width: 100%;
        height: calc(100% - 95px);
        background: #f9fbfc;
    }
</style>
