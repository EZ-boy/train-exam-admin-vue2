<template>
    <div id="con_header">
        <div class="con_left">
            <div style="display: flex">
                <img class="icon_logo" src="@/assets/img/header/icon_logo.png" />
            </div>
            <div class="c_label">培训考试管理系统</div>
            <div class="top_item" @click="change('home')">
                <div style="display: flex">
                    <img v-if="active == 'home'" src="@/assets/img/header/icon_home_check.png" />
                    <img v-else src="@/assets/img/header/icon_home_uncheck.png" />
                </div>
                <div class="top_item_text" :class="{ text_active: active == 'home' }">首页</div>
            </div>
            <div class="top_item" @click="change('train')">
                <div style="display: flex">
                    <img v-if="active == 'train'" src="@/assets/img/header/icon_train_check.png" />
                    <img v-else src="@/assets/img/header/icon_train_uncheck.png" />
                </div>
                <div class="top_item_text" :class="{ text_active: active == 'train' }">培训学习</div>
            </div>
            <div class="top_item" @click="change('center')">
                <div style="display: flex">
                    <img v-if="active == 'center'" src="@/assets/img/header/icon_center_check.png" />
                    <img v-else src="@/assets/img/header/icon_center_uncheck.png" />
                </div>
                <div class="top_item_text" :class="{ text_active: active == 'center' }">个人中心</div>
            </div>
            <div class="top_item" v-show="userRole == 1" @click="change('system')">
                <div style="display: flex">
                    <img v-if="active == 'system'" src="@/assets/img/header/icon_system_check.png" />
                    <img v-else src="@/assets/img/header/icon_system_uncheck.png" />
                </div>
                <div class="top_item_text" :class="{ text_active: active == 'system' }">系统服务</div>
            </div>
        </div>
        <div class="con_right">
            <!-- <div class="right_1">
                <img class="icon_search" @click="search" src="@/assets/img/header/icon_search.png" />
                <el-input v-model="searchValue" placeholder=""></el-input>
            </div> -->
            <div style="display: flex; align-items: center">
                <img class="icon_portrait" src="@/assets/img/header/icon_portrait_woman.png" />
                <!-- <img v-if="$store.state.user.userInfo.staffSex == '02'" src="@/assets/img/header/icon_portrait_woman.png" />
                <img v-if="$store.state.user.userInfo.staffSex == '01'" src="@/assets/img/header/icon_portrait_man.png" /> -->
                <el-dropdown @command="handleCommand">
                    <div class="login_user">{{ userName }}<i class="el-icon-caret-bottom"></i></div>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="logout">退出</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapMutations } from 'vuex';
    import loginApi from '@/api/login';
    export default {
        data() {
            return {
                active: 'home',
                searchValue: '',
                userName: this.$store.getters.userInfo.realName,
                userRole: this.$store.getters.userInfo.role,
            };
        },
        mounted() {
            this.setActiveName();
            console.log(this.userRole);
        },
        methods: {
            search() {
                console.log('searchValue>>>', this.searchValue);
            },
            handleCommand(command) {
                if (command === 'logout') {
                    loginApi.logout().then((result) => {
                        if (result && result.code === 1) {
                            this.clearLogin();
                            this.$router.push({ path: '/login' });
                        }
                    });
                }
            },
            ...mapMutations('user', ['clearLogin']),
            change(name) {
                this.active = name;
                if (name === 'home') {
                    this.$router.push({
                        name,
                        query: {
                            appName: '首页',
                        },
                    });
                } else if (name === 'train') {
                    this.$router.push({
                        name,
                        query: {
                            appName: '培训学习',
                        },
                    });
                } else if (name === 'center') {
                    this.$router.push({
                        name,
                        query: {
                            appName: '个人中心',
                        },
                    });
                } else if (name === 'system') {
                    this.$router.push({
                        name,
                        query: {
                            appName: '系统服务',
                        },
                    });
                }
            },
            setActiveName() {
                const path = this.$router.currentRoute.path;
                console.log('currentRoute>>>>>', path);
                if (path == '/home') {
                    this.active = 'home';
                } else if (path == '/train') {
                    this.active = 'train';
                } else if (path == '/center') {
                    this.active = 'center';
                } else if (path == '/system') {
                    this.active = 'system';
                } else {
                    this.active = '';
                }
            },
        },
    };
</script>

<style lang="scss" scoped>
    #con_header {
        width: 100%;
        height: 95px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 50px 0 220px;
        border-bottom: solid 1px rgba(169, 177, 196, 0.45);
    }
    .con_left {
        display: flex;
        align-items: center;
    }
    .con_right {
        display: flex;
        align-items: center;
    }
    .c_label {
        font-size: 24px;
        color: #1c2421;
    }
    .icon_logo {
        width: 32px;
        height: 38px;
        margin-right: 23px;
    }
    .top_item {
        display: flex;
        align-items: center;
        margin-left: 65px;
        cursor: pointer;
    }
    .top_item img {
        height: 28px;
        margin-right: 20px;
    }
    .top_item_text {
        font-size: 24px;
        color: #717682;
    }
    .text_active {
        color: #1962f8;
    }

    .right_1 {
        position: relative;
        width: 250px;
        height: 32px;
    }
    .icon_search {
        position: absolute;
        top: 0.5rem;
        left: 18px;
        width: 16px;
        height: 16px;
        z-index: 10;
        cursor: pointer;
    }
    .icon_portrait {
        width: 34px;
        height: 34px;
        margin-left: 40px;
        margin-right: 13px;
    }
    .login_user {
        font-size: 16px;
        color: #717682;
        cursor: pointer;
    }
    /deep/ .right_1 .el-input__inner {
        height: 2rem;
        line-height: 2rem;
        padding-left: 40px;
        background-color: #f5f6fa;
        border-radius: 7px;
        border: solid 1px #a9b1c4;
    }
    /deep/ .el-icon-caret-bottom:before {
        margin-left: 10px;
    }
</style>
