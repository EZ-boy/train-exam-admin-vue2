<template>
    <div class="user-profile">
        <div class="left-container">
            <div class="left-container-head font">
                <span>个人中心</span>
                <!-- <div class="left-container-link">全部</div> -->
            </div>
            <!-- 导航条 -->
            <el-tabs v-model="activeName" @tab-click="handleClick" style="height: calc(100% - 2.5rem)">
                <el-tab-pane label="" name="first">
                    <template #label>
                        <div>
                            <img v-show="activeName === 'first'" class="icon" src="@/assets/center_images/study_on.png" alt="" />
                            <img v-show="activeName !== 'first'" class="icon" src="@/assets/center_images/study_off.png" alt="" />
                            学习({{ studyNum }})
                        </div>
                    </template>
                    <div class="left-container-select">
                        <span style="font-weight: 500">学习类型：</span>
                        <el-select v-model="value" placeholder="请选择" @change="selectOption(value)">
                            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"> </el-option>
                        </el-select>
                    </div>
                    <div class="left-container-list" v-if="FormData.length > 0">
                        <div class="list-card" v-for="(item, index) in FormData" :key="index" @click="play(item.fileId, item.title)">
                            <div class="card-pic">
                                <!-- <img v-show="item.pic === ''" src="@/assets/center_images/list_pic.png" alt=""> -->
                                <img src="@/assets/center_images/list_pic.png" alt="" />
                            </div>
                            <div class="card-text">
                                <p class="card-font" style="margin-top: 0.4rem">{{ item.title }}</p>
                                <p class="card-font font">{{ item.subTitle }}</p>
                            </div>
                        </div>
                    </div>
                    <div v-else style="display: flex; justify-content: center; margin-top: 50px">暂无数据</div>
                </el-tab-pane>

                <el-tab-pane label="" name="second">
                    <template #label>
                        <img v-show="activeName === 'second'" class="icon" src="@/assets/center_images/test_on.png" alt="" />
                        <img v-show="activeName !== 'second'" class="icon" src="@/assets/center_images/test_off.png" alt="" />
                        考试({{ TextData.length }})
                    </template>
                    <!-- <div style="display: flex;">
              <div class="left-container-select">
                <span style="font-weight: 500;">考试类型：</span>
                <el-select v-model="optionsValue1" placeholder="请选择">
                  <el-option
                    v-for="item in options1"
                    :key="item.optionsValue"
                    :label="item.label"
                    :value="item.optionsValue">
                  </el-option>
                </el-select>
              </div>
              <div class="left-container-select">
                <span style="font-weight: 500;">通过方式：</span>
                <el-select v-model="optionsValue2" placeholder="请选择">
                  <el-option
                    v-for="item in options2"
                    :key="item.optionsValue"
                    :label="item.label"
                    :value="item.optionsValue">
                  </el-option>
                </el-select>
              </div>
            </div> -->
                    <div class="left-container-list" v-if="TextData.length > 0">
                        <!-- ['list-card', { 'disabled': item.paperItems[0].status !== null }] -->
                        <div
                            class="list-card"
                            v-for="(item, index) in TextData"
                            :key="index"
                            @click="
                                item.paperItems[0].status === null
                                    ? Ontextpage(item.paperItems[0].examPaperId)
                                    : item.paperItems[0].status === 2
                                    ? onScore(item.paperItems[0].examPaperAnswerId)
                                    : ''
                            "
                        >
                            <div class="card-pic">
                                <img v-show="item.pic === ''" src="@/assets/center_images/list_pic.png" alt="" />
                                <img v-show="item.pic !== ''" src="@/assets/center_images/list_pic.png" alt="" />
                            </div>
                            <div class="card-text">
                                <p class="card-font" style="margin-top: 0.4rem">{{ item.taskName }}</p>
                                <p class="card-font font"><span style="color: #1962f8">截止时间:</span>{{ item.limitEndTime }}</p>
                                <div v-show="item.paperItems[0].status === null" class="card-btn">马上考试</div>
                                <div v-show="item.paperItems[0].status == '2'" class="card-btn page-btn">查看试卷</div>
                            </div>
                        </div>
                    </div>
                    <div v-else style="display: flex; justify-content: center; margin-top: 50px">暂无数据</div>
                </el-tab-pane>
            </el-tabs>
        </div>

        <div class="right-container font">
            <div class="user-top">
                <div class="user-msg">
                    <div class="user-pic"><img src="@/assets/center_images/user.png" alt="" /></div>
                    <div class="user-name">
                        <p>{{ userNo }}</p>
                        <p class="font">共学习{{ CurrentTime }}分钟</p>
                    </div>
                </div>
                <div class="user-study">
                    <div class="study">
                        <p>{{ CurrentTime }}</p>
                        <p class="font">学习(分钟)</p>
                    </div>
                    <div class="study">
                        <p>{{ Complete }}</p>
                        <p class="font">已完成培训</p>
                    </div>
                    <div class="study">
                        <p>{{ TextNum }}</p>
                        <p class="font">通过考试</p>
                    </div>
                </div>
            </div>
            <div class="user-bottom">
                <div class="bottom-list">
                    <div class="list-top">
                        <img src="@/assets/center_images/user_book.png" alt="" />
                        <span>我的学习</span>
                    </div>
                    <div class="btn">
                        <img src="@/assets/center_images/in_button.png" alt="" />
                    </div>
                </div>
                <div class="bottom-list">
                    <div class="list-top">
                        <img src="@/assets/center_images/user_rank.png" alt="" />
                        <span>学时间排行</span>
                    </div>
                    <div class="btn">
                        <img src="@/assets/center_images/in_button.png" alt="" />
                    </div>
                </div>
            </div>
        </div>

        <div>
            <!-- 视频播放组件 -->
            <videoPlayer v-if="videoPlayerShow" :videoId="videoId" :title="title" :show.sync="videoPlayerShow" @updateFlag="updateFlag"></videoPlayer>
        </div>
    </div>
</template>

<script>
    import videoPlayer from '@/views/videoPlayer/videoPlayer.vue';
    import centerApi from '@/api/center';

    export default {
        components: {
            videoPlayer,
        },
        data() {
            return {
                userNo: this.$store.getters.userInfo.userName,
                FormData: [],
                studyNum: '',
                CurrentTime: '',
                Complete: '',
                title: '',
                TextData: [],
                TextNum: '',
                activeName: 'first',
                options: [
                    {
                        value: '',
                        label: '',
                    },
                    {
                        value: '01',
                        label: '视频',
                    },
                    {
                        value: '02',
                        label: '文档',
                    },
                    {
                        value: '03',
                        label: '其他',
                    },
                ],
                value: '',
                options1: [
                    {
                        value: '01',
                        label: '视频',
                    },
                    {
                        value: '02',
                        label: '文档',
                    },
                    {
                        value: '03',
                        label: '其他',
                    },
                ],
                optionsValue1: '',
                options2: [
                    {
                        value: '01',
                        label: '视频',
                    },
                    {
                        value: '02',
                        label: '文档',
                    },
                    {
                        value: '03',
                        label: '其他',
                    },
                ],
                optionsValue2: '',
                //视频播放
                videoId: '1',
                videoPlayerShow: false,
            };
        },
        created() {
            this.getinit();
            this.getUserText();
        },
        mounted() {},
        methods: {
            handleClick(tab, event) {
                console.log(tab, event);
            },
            selectOption(value) {
                if ((this.value = '')) {
                    this.getinit();
                }
                this.value = value;
                // console.log(this.value)
                this.getFileTypeList();
            },
            getinit() {
                let params = { userNo: this.userNo };
                centerApi.getUserTrainList(params).then((data) => {
                    this.FormData = data.response;
                    this.studyNum = Object.keys(data.response).length;
                    this.getUserTime(data.response);
                    this.getUsercomplete(data.response);
                });
            },
            getFileTypeList() {
                let params = { userNo: this.userNo, fileType: this.value };
                centerApi.getUserTrainList(params).then((data) => {
                    this.FormData = data.response;
                    console.log('培训任务', data.response);
                });
            },
            getUserText() {
                centerApi.getUserTextList().then((data) => {
                    this.TextData = data.response;
                    console.log('考试任务', data.response);
                    this.TextNum = this.getTextNum(data.response);
                });
            },
            Ontextpage(id) {
                this.$router.push({
                    name: 'ExamPaperDo',
                    query: {
                        id: id,
                    },
                });
            },
            getUserTime(data) {
                let currentTime = Object.values(data).reduce((total, obj) => {
                    if (obj.currentTime) {
                        return total + obj.currentTime;
                    } else {
                        return total;
                    }
                }, 0);
                this.CurrentTime = parseFloat(currentTime / 60).toFixed(1);
                // console.log('学习时长',this.currentTime);
            },
            getUsercomplete(data) {
                let count = 0;
                Object.values(data).forEach((obj) => {
                    if (obj.studyProgress >= 100) {
                        count++;
                    }
                });
                this.Complete = count;
            },
            getTextNum(response) {
                const count = response.reduce((totalCount, obj) => {
                    const nullStatusCount = obj.paperItems.reduce((itemCount, item) => {
                        if (item.status !== null) {
                            itemCount++;
                        }
                        return itemCount;
                    }, 0);

                    return totalCount + nullStatusCount;
                }, 0);

                return count;
            },
            play(videoId, title) {
                this.videoId = videoId;
                this.title = title;
                this.videoPlayerShow = true;
            },
            updateFlag() {},
            onScore(id) {
                console.log('试卷id', id);
                const { href } = this.$router.resolve({
                    name: 'ExamPaperRead',
                    query: { id: id },
                });
                window.open(href, '_blank');
                /*  this.$router.push({
                name: 'ExamPaperRead',
                query: {
                  id: id
                }
              })*/
            },
        },
    };
</script>

<style lang="scss" scoped>
    .user-profile {
        display: flex;
        width: 86.875rem;
        margin-left: 18.75rem;
        height: 100%;
        padding: 2.8rem 0;
        color: #1c2227;
        text-shadow: 0 0 #1c2227;
    }
    .icon {
        width: 10px;
        height: 10px;
    }
    .font {
        font-size: 14px;
        color: #a9b1c4;
        text-shadow: 0 0 #a9b1c4;
        font-weight: 400;
        line-height: 20px;
        letter-spacing: 1px;
    }
    /deep/.el-tabs__content {
        height: calc(100% - 3.5rem);
    }
    /deep/.el-tab-pane {
        height: calc(100% - 0.5rem);
    }
    /deep/ .el-tabs__active-bar {
        width: 66px;
        height: 1px;
    }
    /deep/.el-tabs__nav-wrap::after {
        height: 1px;
    }
    /deep/ .el-tabs__item {
        font-size: 1.075rem;
    }
    .left-container {
        width: 90%;
        height: 100%;
        background-color: #fff;
        padding: 20px 10px;
        border-radius: 4px;
        border: 1px solid #eee;
        .left-container-head {
            height: 2.75rem;
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            font-weight: 600;
            font-size: 1.4rem;
            color: #1c2227;
            text-shadow: 0 0 #1c2227;
            .left-container-link {
                color: #1962f8;
                font-size: 1rem;
            }
        }
        .left-container-select {
            margin-top: 10px;
            margin-left: 20px;
            font-weight: 600;
        }
        .left-container-list {
            height: calc(100% - 3.5rem);
            display: flex;
            flex-wrap: wrap;
            align-content: flex-start;
            overflow: auto;
            margin-top: 10px;
            font-weight: 500;
            .disabled {
                opacity: 0.2;
                pointer-events: none;
            }
            .list-card {
                width: 30rem;
                height: 8rem;
                display: flex;
                padding: 10px;
                border: 1px solid #eeeeee;
                border-radius: 4px;
                margin-top: 20px;
                margin-left: 10px;
                cursor: pointer;
                .card-text {
                    padding-left: 10px;
                    font-size: 16px;
                    font-weight: 600;
                    .card-font {
                        height: 1.1rem;
                        width: 16rem;
                        white-space: nowrap;
                        text-overflow: ellipsis;
                        overflow: hidden;
                        font-weight: 500;
                    }
                }
                .card-btn {
                    background-color: #c4f4e8;
                    color: #53ccac;
                    width: 5rem;
                    text-align: center;
                    padding: 2px 5px;
                    border-radius: 12px;
                    border: solid 1px #53ccac;
                    margin-left: 11rem;
                    font-weight: 500;
                }
                .page-btn {
                    background-color: #c4dff4;
                    color: #53accc;
                    border: solid 1px #53accc;
                }
            }
        }
    }

    .right-container {
        width: 30%;
        height: 100%;
        margin-left: 10px;
        overflow: auto;
        .user-top {
            width: 100%;
            height: 17rem;
            background-image: url('~@/assets/center_images/user_bg.png');
            background-size: 100%;
            display: flex;
            flex-direction: column;
            border-radius: 4px;
            .user-msg {
                display: flex;
                flex-direction: row;
                align-items: center;
                padding-top: 80px;
                padding-left: 50px;

                .user-pic {
                    width: 5rem;
                    height: 5rem;
                    border-radius: 50%;
                    img {
                        widows: 100%;
                        height: 100%;
                    }
                }
                .user-name {
                    margin-left: 30px;
                    color: #1c2227;
                    text-shadow: 0 0 #1c2227;
                    font-size: 1.1rem;
                    font-weight: 600;
                    line-height: 4px;
                }
            }
            .user-study {
                display: flex;
                flex-direction: row;
                justify-content: space-around;
                margin-top: 2rem;
                color: #1c2227;
                text-shadow: 0 0 #1c2227;
                font-size: 1.1rem;
                font-weight: 600;
                text-align: center;
                line-height: 0px;
            }
        }

        .bottom-list {
            padding: 10px 16px;
            background-color: #fff;
            border-radius: 4px;
            border: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
            color: #1c2227;
            text-shadow: 0 0 #1c2227;
            font-size: 1.1rem;
            font-weight: 600;
            .list-top {
                display: flex;
                align-items: center;
                span {
                    margin-left: 10px;
                    font-size: 12px;
                }
            }
            img {
                width: 1.4rem;
                height: 1.4rem;
            }
            .btn img {
                width: 0.7rem;
                height: 1.2rem;
            }
        }
    }
</style>
