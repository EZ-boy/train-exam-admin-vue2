<template>
    <div class="lowin lowin-blue">
        <div class="background-image">
            <div class="lowin-title">
                <p style="margin: 0">培训考试系统</p>
                <p>数字·智能·高效</p>
            </div>
            <div class="lowin-wrapper">
                <div class="lowin-box lowin-login">
                    <div class="lowin-box-inner" v-show="loginType === 'login'">
                        <el-form ref="loginForm" :model="loginForm" :rules="loginRules">
                            <div class="lowin-group">
                                <img class="icon-button" src="../../assets/login_images/user.png" alt="" />
                                <el-input
                                    ref="userName"
                                    v-model="loginForm.userName"
                                    class="lowin-input"
                                    placeholder="请输入账号名"
                                    name="userName"
                                    type="text"
                                    tabindex="1"
                                    auto-complete="on"
                                />
                            </div>
                            <div class="lowin-group password-group">
                                <img class="icon-button" src="../../assets/login_images/psw.png" alt="" />
                                <el-input
                                    class="lowin-input"
                                    :key="passwordType"
                                    ref="password"
                                    v-model="loginForm.password"
                                    :type="passwordType"
                                    placeholder="请输入密码"
                                    name="password"
                                    tabindex="2"
                                    auto-complete="on"
                                    @keyup.native="checkCapslock"
                                    @blur="capsTooltip = false"
                                    @keyup.enter.native="handleLogin"
                                />
                            </div>
                            <div class="lowin-group remember-group">
                                <el-checkbox v-model="rememberPassword">记住密码</el-checkbox>
                                <label>
                                    <div class="psw">
                                        <img class="psw-icon" src="../../assets/login_images/password Icon.png" alt="" />
                                        <a @click="changeLoginType('update')" class="forgot-link">修改密码</a>
                                    </div>
                                </label>
                            </div>
                            <el-button :loading="loading" type="text" class="lowin-btn login-btn" @click.native.prevent="handleLogin">登录</el-button>
                        </el-form>
                    </div>
                    <div class="lowin-box-inner" v-show="loginType === 'update'">
                        <el-form ref="updateForm" :model="updateForm" :rules="updateRules">
                            <el-form-item label="账号" prop="userName">
                                <el-input v-model="updateForm.userName"></el-input>
                            </el-form-item>
                            <el-form-item label="原密码" prop="password">
                                <el-input v-model="updateForm.password" :type="passwordType"></el-input>
                            </el-form-item>
                            <el-form-item label="新密码" prop="newPwd">
                                <el-input v-model="updateForm.newPwd" :type="passwordType"></el-input>
                            </el-form-item>
                            <el-form-item label="确认密码" prop="surePwd">
                                <el-input v-model="updateForm.surePwd" :type="passwordType"></el-input>
                            </el-form-item>
                            <el-button :loading="loading1" type="text" class="lowin-btn1 login-btn" @click.native.prevent="updatePwd">修改</el-button>
                            <label style="margin: 10px 0 0 0">
                                <div class="psw">
                                    <a @click="changeLoginType('login')" class="forgot-link">去登录</a>
                                </div>
                            </label>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { mapMutations } from 'vuex';
    import loginApi from '@/api/login';
    import userApi from '@/api/user';
    import { encrypt } from '@/utils/jsencrypt';

    export default {
        name: 'Login',
        data() {
            const validateUsername = (rule, value, callback) => {
                if (value.length < 5) {
                    callback(new Error('用户名不能少于5个字符'));
                } else {
                    callback();
                }
            };
            const validatePassword = (rule, value, callback) => {
                if (value.length < 5) {
                    callback(new Error('密码不能少于6个字符'));
                } else {
                    callback();
                }
            };
            const validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.updateForm.newPwd) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                loginType: 'login',
                loginForm: {
                    userName: '',
                    password: '',
                    remember: false,
                },
                updateForm: {
                    userName: '',
                    password: '',
                    newPwd: '',
                    surePwd: '',
                },
                loginRules: {
                    userName: [{ required: true, trigger: 'blur', validator: validateUsername }],
                    password: [{ required: true, trigger: 'blur', validator: validatePassword }],
                },
                updateRules: {
                    userName: [{ required: true, trigger: 'blur', validator: validateUsername }],
                    password: [{ required: true, trigger: 'blur', validator: validatePassword }],
                    newPwd: [{ required: true, trigger: 'blur', validator: validatePassword }],
                    surePwd: [{ required: true, trigger: 'blur', validator: validatePass2 }],
                },
                passwordType: 'password',
                capsTooltip: false,
                loading: false,
                loading1: false,
                showDialog: false,
                rememberPassword: false,
            };
        },
        created() {
            // window.addEventListener('storage', this.afterQRScan)
            const savedUsername = localStorage.getItem('username');
            const savedPassword = localStorage.getItem('password');
            if (savedUsername && savedPassword) {
                this.loginForm.userName = savedUsername;
                this.loginForm.password = savedPassword;
                this.rememberPassword = true;
            }
        },
        mounted() {
            if (this.loginForm.userName === '') {
                this.$refs.userName.focus();
            } else if (this.loginForm.password === '') {
                this.$refs.password.focus();
            }
        },
        destroyed() {
            // window.removeEventListener('storage', this.afterQRScan)
        },
        methods: {
            updatePwd() {
                this.$refs.updateForm.validate((valid) => {
                    if (valid) {
                        this.loading1 = true;
                        userApi
                            .updatePwd({
                                userName: this.updateForm.userName,
                                password: encrypt(this.updateForm.password),
                                newPwd: encrypt(this.updateForm.newPwd),
                            })
                            .then((result) => {
                                console.log(result);
                                if (result.response.code === '200') {
                                    this.$message({
                                        message: result.response.msg,
                                        type: 'success',
                                    });
                                    this.loading1 = false;
                                    this.loginType = 'login';
                                } else {
                                    this.$message({
                                        message: result.response.msg,
                                        type: 'error',
                                    });
                                    this.loading1 = false;
                                }
                                /* if (result && result.code === 1) {
                                    _this.loading1 = false;
                                } else {
                                    _this.loading1 = false;
                                    _this.$message({
                                        message: result.message,
                                        type: 'error',
                                    });
                                } */
                            })
                            .catch(function (reason) {
                                this.loading1 = false;
                            });
                    } else {
                        return false;
                    }
                });
            },
            changeLoginType(val) {
                this.loginType = val;
                if (val === 'login') {
                    this.loginForm = {
                        userName: '',
                        password: '',
                        remember: false,
                    };
                } else {
                    this.updateForm = {
                        userName: '',
                        password: '',
                        newPwd: '',
                        surePwd: '',
                    };
                }
            },
            checkCapslock({ shiftKey, key } = {}) {
                if (key && key.length === 1) {
                    // eslint-disable-next-line no-mixed-operators
                    if ((shiftKey && key >= 'a' && key <= 'z') || (!shiftKey && key >= 'A' && key <= 'Z')) {
                        this.capsTooltip = true;
                    } else {
                        this.capsTooltip = false;
                    }
                }
                if (key === 'CapsLock' && this.capsTooltip === true) {
                    this.capsTooltip = false;
                }
            },
            showPwd() {
                if (this.passwordType === 'password') {
                    this.passwordType = '';
                } else {
                    this.passwordType = 'password';
                }
                this.$nextTick(() => {
                    this.$refs.password.focus();
                });
            },
            handleLogin() {
                let _this = this;
                if (this.rememberPassword) {
                    localStorage.setItem('username', this.loginForm.userName);
                    localStorage.setItem('password', this.loginForm.password);
                } else {
                    localStorage.removeItem('username');
                    localStorage.removeItem('password');
                }
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        loginApi
                            .login({ ...this.loginForm, password: encrypt(this.loginForm.password) })
                            .then(function (result) {
                                if (result && result.code === 1) {
                                    _this.setUserName(_this.loginForm.userName);
                                    userApi.getCurrentUser().then((res) => {
                                        _this.setUserInfo(res.response);
                                        _this.$router.push({ path: '/home' });
                                        _this.loading = false;
                                    });
                                } else {
                                    _this.loading = false;
                                    _this.$message({
                                        message: result.message,
                                        type: 'error',
                                    });
                                }
                            })
                            .catch(function (reason) {
                                _this.loading = false;
                            });
                    } else {
                        return false;
                    }
                });
            },
            ...mapMutations('user', ['setUserName', 'setUserInfo']),
        },
    };
</script>

<style lang="scss">
    .lowin-input {
        .el-input__inner {
            background-color: transparent !important;
            border: 0px !important;
        }
    }
</style>

<style scoped>
    .lowin {
        /* variables */
        --color-primary: #44a0b3;
        --color-grey: rgba(68, 160, 179, 0.06);
        --color-dark: rgba(68, 160, 179, 0.5);
        --color-semidark: rgba(68, 160, 179, 0.5);

        text-align: center;
        /* margin: 60px 0 0 0; */
        font-family: 'Segoe UI';
        font-size: 14px;
        height: 100%;
    }
    .background-image {
        display: flex;
        background-image: url('../../assets/login_images/background.png');
        background-size: cover;
        background-position: center;
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
    }
    .lowin-title {
        position: absolute;
        top: 100px;
        left: 200px;
        color: #4960fe;
        font-weight: bold;
        font-size: 32px;
        letter-spacing: 10px;
        display: flex;
        flex-direction: column;
        align-items: flex-start;
    }
    .lowin-title p:nth-of-type(2) {
        color: #8d98ac;
        font-size: 18px;
        font-weight: 500;
    }
    .lowin .lowin-wrapper {
        -webkit-transition: all 1s;
        -o-transition: all 1s;
        transition: all 1s;
        -webkit-perspective: 1000px;
        perspective: 1000px;
        height: 100%;
        width: 360px;
        margin: 0 auto;
        position: absolute;
        right: 200px;
        padding-top: 140px;
    }

    .lowin.lowin-blue {
        --color-primary: #0081c6;
        --color-grey: rgba(0, 129, 198, 0.05);
        --color-dark: rgba(0, 129, 198, 0.7);
        --color-semidark: rgba(0, 129, 198, 0.45);
    }

    .lowin a {
        color: #0966ffc9;
        /* text-decoration: none;
    border-bottom: 1px dashed var(--color-semidark);
    margin-top: -3px;
    padding-bottom: 2px; */
        text-decoration: none;
    }
    /deep/ .el-checkbox .el-checkbox__label {
        color: #c4c4c4;
        font-weight: 500;
    }

    /deep/ .el-checkbox .el-checkbox__input.is-checked + .el-checkbox__label {
        color: #c4c4c4;
        font-weight: 500;
    }

    /deep/ .el-checkbox .el-checkbox__inner {
        border-color: #8ac7ff;
        background-color: rgb(186 222 255 / 50%);
    }
    /deep/ .el-checkbox__inner::after {
        border: 1px solid #3a8ee6;
        border-left: 0;
        border-top: 0;
    }
    /deep/.lowin-input .el-input__inner {
        padding: 0;
    }
    .lowin * {
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
    }
    .lowin .lowin-brand {
        overflow: hidden;
        width: 100px;
        height: 100px;
        margin: 0 auto -50px auto;
        border-radius: 50%;
        -webkit-box-shadow: 0 4px 40px rgba(0, 0, 0, 0.07);
        box-shadow: 0 4px 40px rgba(0, 0, 0, 0.07);
        padding: 10px;
        background-color: #fff;
        z-index: 1;
        position: relative;
    }

    .lowin .lowin-brand img {
        width: 100%;
    }

    .lowin .lowin-box {
        width: 100%;
        position: absolute;
        left: 0;
    }

    .lowin .lowin-box-inner {
        background-color: #fff;
        -webkit-box-shadow: 0 7px 25px rgba(0, 0, 0, 0.08);
        box-shadow: 0 7px 25px rgba(0, 0, 0, 0.08);
        padding: 60px 25px 100px 25px;
        text-align: left;
        border-radius: 3px;
    }
    .psw {
        display: flex;
        float: right;
    }
    .psw-icon {
        margin-right: 10px;
        width: 15px;
        height: 15px;
    }
    .forgot-link {
        line-height: 12px;
    }

    .lowin .lowin-box::after {
        content: ' ';
        -webkit-box-shadow: 0 0 25px rgba(0, 0, 0, 0.1);
        box-shadow: 0 0 25px rgba(0, 0, 0, 0.1);
        -webkit-transform: translate(0, -92.6%) scale(0.88);
        -ms-transform: translate(0, -92.6%) scale(0.88);
        transform: translate(0, -92.6%) scale(0.88);
        border-radius: 3px;
        /* position: absolute;
    top: 100%;
    left: 0;
    width: 100%;
    height: 100%; */
        background-color: #fff;
        z-index: -1;
    }

    .lowin .lowin-box.lowin-flip {
        -webkit-transform: rotate3d(0, 1, 0, -180deg);
        transform: rotate3d(0, 1, 0, -180deg);
        display: none;
        opacity: 0;
    }

    .lowin .lowin-box p {
        color: var(--color-semidark);
        font-weight: 700;
        margin-bottom: 20px;
        text-align: center;
    }

    .lowin .lowin-box .lowin-group {
        margin-bottom: 30px;
        display: flex;
        align-items: center;
    }
    .icon-button {
        width: 25px;
        height: 20px;
        margin-right: 20px;
    }

    .lowin .lowin-box label {
        margin-bottom: 5px;
        display: inline-block;
        width: 100%;
        color: var(--color-semidark);
        font-weight: 700;
        margin-left: 42px;
    }

    .lowin .lowin-box label a {
        float: right;
    }

    .lowin .lowin-box .lowin-input {
        background-color: #8d98ac14;
        color: #dedede;
        border: none;
        border-radius: 3px;
        padding: 0 20px;
        width: 100%;
        outline: 0;
        border: 1px solid;
    }

    .lowin .lowin-box .lowin-btn {
        display: inline-block;
        margin-left: 42px;
        width: 268px;
        border: none;
        color: #fff;
        /* padding: 15px; */
        border-radius: 3px;
        background: linear-gradient(to right, #2641ff, #2ca1ff);
        -webkit-box-shadow: 0 2px 7px var(--color-semidark);
        box-shadow: 0 2px 7px var(--color-semidark);
        font-weight: 700;
        outline: 0;
        cursor: pointer;
        -webkit-transition: all 0.5s;
        -o-transition: all 0.5s;
        transition: all 0.5s;
    }
    .lowin .lowin-box .lowin-btn1 {
        display: inline-block;
        width: 100%;
        border: none;
        color: #fff;
        /* padding: 15px; */
        border-radius: 3px;
        background: linear-gradient(to right, #2641ff, #2ca1ff);
        -webkit-box-shadow: 0 2px 7px var(--color-semidark);
        box-shadow: 0 2px 7px var(--color-semidark);
        font-weight: 700;
        outline: 0;
        cursor: pointer;
        -webkit-transition: all 0.5s;
        -o-transition: all 0.5s;
        transition: all 0.5s;
    }

    .lowin .lowin-box .lowin-btn:active {
        /* -webkit-box-shadow: none;
    box-shadow: none; */
    }

    .lowin .lowin-box .lowin-btn:hover {
        opacity: 0.9;
    }

    .lowin .text-foot {
        text-align: center;
        padding: 10px;
        font-weight: 700;
        margin-top: 20px;
        color: var(--color-semidark);
    }

    /* animation */
    .lowin .lowin-box.lowin-animated {
        -webkit-animation-name: LowinAnimated;
        animation-name: LowinAnimated;
        -webkit-animation-duration: 1s;
        animation-duration: 1s;
        -webkit-animation-fill-mode: forwards;
        animation-fill-mode: forwards;
        -webkit-animation-timing-function: ease-in-out;
        animation-timing-function: ease-in-out;
    }

    .lowin .lowin-box.lowin-animatedback {
        -webkit-animation-name: LowinAnimatedBack;
        animation-name: LowinAnimatedBack;
        -webkit-animation-duration: 1s;
        animation-duration: 1s;
        -webkit-animation-fill-mode: forwards;
        animation-fill-mode: forwards;
        -webkit-animation-timing-function: ease-in-out;
        animation-timing-function: ease-in-out;
    }

    .lowin .lowin-box.lowin-animated-flip {
        -webkit-animation-name: LowinAnimatedFlip;
        animation-name: LowinAnimatedFlip;
        -webkit-animation-duration: 1s;
        animation-duration: 1s;
        -webkit-animation-fill-mode: forwards;
        animation-fill-mode: forwards;
        -webkit-animation-timing-function: ease-in-out;
        animation-timing-function: ease-in-out;
    }

    .lowin .lowin-box.lowin-animated-flipback {
        -webkit-animation-name: LowinAnimatedFlipBack;
        animation-name: LowinAnimatedFlipBack;
        -webkit-animation-duration: 1s;
        animation-duration: 1s;
        -webkit-animation-fill-mode: forwards;
        animation-fill-mode: forwards;
        -webkit-animation-timing-function: ease-in-out;
        animation-timing-function: ease-in-out;
    }

    .lowin .lowin-brand.lowin-animated {
        -webkit-animation-name: LowinBrandAnimated;
        animation-name: LowinBrandAnimated;
        -webkit-animation-duration: 1s;
        animation-duration: 1s;
        -webkit-animation-fill-mode: forwards;
        animation-fill-mode: forwards;
        -webkit-animation-timing-function: ease-in-out;
        animation-timing-function: ease-in-out;
    }

    .lowin .lowin-group.password-group {
        -webkit-transition: all 1s;
        -o-transition: all 1s;
        transition: all 1s;
    }

    .lowin .lowin-group.password-group.lowin-animated {
        -webkit-animation-name: LowinPasswordAnimated;
        animation-name: LowinPasswordAnimated;
        -webkit-animation-duration: 1s;
        animation-duration: 1s;
        -webkit-animation-fill-mode: forwards;
        animation-fill-mode: forwards;
        -webkit-animation-timing-function: ease-in-out;
        animation-timing-function: ease-in-out;
        -webkit-transform-origin: 0 0;
        -ms-transform-origin: 0 0;
        transform-origin: 0 0;
    }

    .lowin .lowin-group.password-group.lowin-animated-back {
        -webkit-animation-name: LowinPasswordAnimatedBack;
        animation-name: LowinPasswordAnimatedBack;
        -webkit-animation-duration: 1s;
        animation-duration: 1s;
        -webkit-animation-fill-mode: forwards;
        animation-fill-mode: forwards;
        -webkit-animation-timing-function: ease-in-out;
        animation-timing-function: ease-in-out;
        -webkit-transform-origin: 0 0;
        -ms-transform-origin: 0 0;
        transform-origin: 0 0;
    }
    .lowin .lowin-group.remember-group {
        display: flex;
        align-items: flex-end;
    }

    @media screen and (max-width: 320px) {
        .lowin .lowin-wrapper {
            width: 100%;
        }
        .lowin .lowin-box {
            padding: 0 10px;
        }
    }
</style>
