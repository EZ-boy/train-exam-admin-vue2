<template>
    <div id="videoPlayer" v-show="value">
        <div class="c_close">
            <div class="c_title">{{ title }}</div>
            <img @click="closeVideo" src="@/assets/img/video/icon_close.png" />
        </div>
        <video-player
            class="video-player vjs-custom-skin"
            ref="videoPlayer"
            :playsinline="true"
            :options="playerOptions"
            @play="onPlayerPlay($event)"
            @pause="onPlayerPause($event)"
            @ended="onPlayerEnded($event)"
            @loadeddata="onPlayerLoadeddata($event)"
            @waiting="onPlayerWaiting($event)"
            @playing="onPlayerPlaying($event)"
            @timeupdate="onPlayerTimeupdate($event)"
            @canplay="onPlayerCanplay($event)"
            @canplaythrough="onPlayerCanplaythrough($event)"
            @ready="onPlayerReadied"
            @statechanged="onPlayerStateChanged($event)"
        ></video-player>
    </div>
</template>

<script>
    import trainApi from '@/api/train.js';
    export default {
        props: {
            show: {
                type: Boolean,
                default: false,
            },
            // src: {
            //     type: String,
            //     default: '',
            //     required: true,
            // },
            videoId: {
                type: String,
                default: '',
                required: true,
            },
            title: {
                type: String,
                default: '',
                required: true,
            },
        },
        data() {
            return {
                userNo: this.$store.getters.userInfo.userName,
                orgNo: this.$store.getters.userInfo.orgNo,
                duration: 0, // 视频总长
                player: null,
                lastTime: 0,

                playTime: 0,
                paused: true,
                src: '',
                playerOptions: {},
            };
        },
        computed: {
            // player() {
            //     return this.$refs.videoPlayer.player;
            // },
            value: {
                get() {
                    return this.show;
                },
                set(val) {
                    this.$emit('update:show', val);
                },
            },
        },
        watch: {
            duration() {
                this.continuePlay();
            },
        },
        mounted() {
            console.log('《用户信息》', this.$store.getters.userInfo);
            this.getPlayerSources();
        },
        methods: {
            getPlayerSources() {
                this.src = process.env.VUE_APP_URL + 'api/file/download?fileId=' + this.videoId;
                console.log('this.src======================');
                console.log(this.src);
                this.setPlayerOptions();
            },
            setPlayerOptions() {
                this.playerOptions = {
                    playbackRates: [0.7, 1.0, 1.5, 2.0], // 播放速度
                    autoplay: false, // 如果true,浏览器准备好时开始回放。
                    muted: false, // 默认情况下将会消除任何音频。
                    loop: false, // 导致视频一结束就重新开始。
                    preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
                    language: 'zh-CN',
                    aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
                    fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
                    sources: [
                        {
                            // src: '../../static/video/test.mp4', // 路径
                            // src: 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4',
                            src: this.src,
                            type: 'video/mp4', // 类型
                        },
                    ],
                    poster: '', // 你的封面地址
                    notSupportedMessage: '此视频暂无法播放，请稍后再试', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
                    controlBar: {
                        timeDivider: true, // 当前时间和持续时间的分隔符
                        durationDisplay: true, // 时长显示
                        remainingTimeDisplay: false, // 剩下时间
                        currentTimeDisplay: true, // 当前时间
                        volumeControl: true, // 声音控制键
                        playToggle: true, // 暂停和播放键
                        progressControl: true, // 进度条
                        fullscreenToggle: true, // 全屏按钮
                    },
                };
            },
            //监听播放状态改变
            onPlayerStateChanged(player) {
                // console.log('onPlayerStateChanged', player);
            },
            //DOM元素上的readyState更改导致播放停止。
            onPlayerWaiting(player) {
                // console.log('onPlayerWaiting', player);
            },
            //媒体不再被禁止播放，并且已开始播放。
            onPlayerPlaying(player) {
                // console.log('onPlayerPlaying', player);
            },
            //当播放器在当前播放位置下载数据时触发
            onPlayerLoadeddata(player) {
                // console.log('onPlayerLoadeddata', player);
            },
            //当前播放位置发生变化时触发。
            onPlayerTimeupdate(player) {
                this.playTime = player.cache_.currentTime;
                // console.log('onPlayerTimeupdate', this.playTime);
            },
            //媒体的readyState为HAVE_FUTURE_DATA或更高
            onPlayerCanplay(player) {
                this.player = player;
                this.duration = player.duration();
            },
            //媒体的readyState为HAVE_ENOUGH_DATA或更高。这意味着可以在不缓冲的情况下播放整个媒体文件。
            onPlayerCanplaythrough(player) {
                // console.log('onPlayerCanplaythrough', player);
            },
            //将侦听器绑定到组件的就绪状态。与事件监听器的不同之处在于，如果ready事件已经发生，它将立即触发该函数。。
            onPlayerReadied(player) {
                // console.log('onPlayerReadied', player);
            },
            //播放
            onPlayerPlay(player) {
                this.paused = false;
                console.log('视频播放!', player);
            },
            //暂停
            onPlayerPause(player) {
                this.paused = true;
                this.saveLastTime(this.playTime);
                console.log('视频播放暂时!', player);
            },
            // 结束
            onPlayerEnded(player) {
                this.paused = false;
                this.saveLastTime(this.playTime);
                console.log('this.playTime', this.playTime);
                console.log('视频播放结束!', player);
            },
            // 续播
            continuePlay() {
                let params = {
                    fileId: this.videoId,
                    userNo: this.userNo,
                };
                trainApi.queryStudyProgress(params).then((data) => {
                    console.log('当前学习进度', data);
                    if (data.response == null) {
                        //没有学习记录新增一条 记录总时长
                        this.insertStudyProgress();
                    } else {
                        const res = data.response;
                        let currentTime = res.currentTime;
                        this.lastTime = currentTime;
                        console.log('this.lastTime', this.lastTime);
                        console.log('this.videoId++++++++++++++++++++', this.videoId);
                        console.log('视频总时长', this.duration);
                        if (!this.value) {
                            return false;
                        }
                        if (this.lastTime > 0 && this.lastTime < this.duration) {
                            this.$confirm('上次观看到' + this.getTimes(this.lastTime) + '，是否继续播放?', '提示', {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning',
                            })
                                .then(() => {
                                    // 自动跳转到对应的位置并播放
                                    this.player.currentTime(this.lastTime);
                                    this.player.play();
                                })
                                .catch(() => {
                                    this.player.currentTime(0);
                                });
                        } else {
                            this.player.currentTime(0);
                        }
                    }
                });
            },
            //新增学习记录数据
            insertStudyProgress() {
                let params = {
                    fileId: this.videoId,
                    userNo: this.userNo,
                    currentTime: 0,
                    totalTime: this.duration,
                    studyProgress: 0,
                    isCompleted: '02',
                };
                trainApi.insertStudyProgress(params).then((data) => {
                    if (data.response == 1) {
                        console.log('insertStudyProgress------------------', data);
                    }
                });
            },
            //保存当前视频播放位置
            saveLastTime(currentTime) {
                console.log('saveLastTime>>>>>>>>>', currentTime);
                this.lastTime = currentTime;
                let studyProgress = parseInt(((currentTime / this.duration) * 100).toFixed(0));
                let params = {
                    fileId: this.videoId,
                    userNo: this.userNo,
                    currentTime: currentTime,
                    studyProgress: studyProgress,
                };
                if (studyProgress == 100) {
                    params.isCompleted = '01';
                }
                trainApi.updateStudyProgress(params).then((data) => {
                    console.log('updateStudyProgress>>>>>>>>>', data);
                    if (data.response == 1) {
                        this.$emit('updateFlag', true);
                    }
                });
            },
            closeVideo() {
                this.saveLastTime(this.playTime);
                this.$emit('updateFlag', true);
                this.value = false;
            },
            getTimes(t) {
                let h = parseInt((t / 60 / 60) % 24);
                let m = parseInt((t / 60) % 60);
                let s = parseInt(t % 60);
                // h = h < 10 ? '0' + h : h;
                // m = m < 10 ? '0' + m : m;
                // s = s < 10 ? '0' + s : s;
                return `${h}小时${m}分钟${s}秒`;
            },
        },
    };
</script>

<style lang="scss" scoped>
    /deep/ .video-js .vjs-big-play-button {
        /*
		 播放按钮换成圆形
		*/
        height: 2em;
        width: 2em;
        line-height: 2em;
        border-radius: 1em;
    }
    #videoPlayer {
        position: absolute;
        width: 60%;
        top: 120px;
        left: 20%;
        z-index: 100;
    }
    .c_title {
        font-size: 18px;
        color: #fff;
    }
    .c_close {
        position: absolute;
        right: 0;
        top: 0;
        width: 100%;
        height: 2.5rem;
        background-color: rgba(51, 51, 51, 0.5);
        z-index: 100;
        padding: 0 0.625rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .c_close img {
        width: 26px;
        height: 26px;
        cursor: pointer;
    }
</style>
