<template>
    <div class="train_box">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="培训" name="first"
                ><div class="box_top">
                    <div class="t_row_1">
                        <div class="label_1" style="width: 65px">类型</div>
                        <div>
                            <el-checkbox-group v-model="trainTypeList">
                                <!-- <el-checkbox label="学习"></el-checkbox>
                        <el-checkbox label="考试"></el-checkbox> -->
                                <el-checkbox label="视频"></el-checkbox>
                                <el-checkbox label="文档"></el-checkbox>
                                <el-checkbox label="其他"></el-checkbox>
                            </el-checkbox-group>
                        </div>
                    </div>
                    <div class="t_row_2">
                        <div class="label_1 t_row_2_1">分类</div>
                        <div class="t_row_2_2">
                            <div class="class_item" :class="{ class_item_active: trainClass == '' }" @click="trainClassClick('', '全部')">全部</div>
                            <div
                                class="class_item"
                                :class="{ class_item_active: trainClass == item.id }"
                                @click="trainClassClick(item.id, item.major)"
                                v-for="(item, index) in trainClassList"
                                :key="index"
                            >
                                {{ item.major }}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box_bottom">
                    <div class="box_bottom_title">
                        <div style="display: flex; align-items: center">
                            <img id="icon_trainClass" src="@/assets/img/train/icon_trainClass.png" />
                            <span class="label_2">{{ trainClassName }}培训</span>
                        </div>
                        <el-input v-model="searchContent" @input="getTrain" style="width: 300px" placeholder="请输入标题关键字搜索"></el-input>
                        <!-- <div class="label_3">全部</div> -->
                    </div>
                    <div class="box_bottom_con">
                        <div class="con_card" @click="play(item.fileId, item.title)" v-for="(item, index) in trainData" :key="index">
                            <!--实操-->
                            <img v-show="item.practicalOperation == '01'" class="icon_train_sc" src="@/assets/img/train/icon_train_sc.png" />
                            <div style="display: flex; height: 4.8125rem">
                                <div><img class="icon_card_zs" src="@/assets/img/train/icon_card_zs.png" /></div>
                                <div>
                                    <div class="label_4" style="height: 50%">{{ item.title }}</div>
                                    <div style="height: 50%; display: flex; align-items: flex-end">
                                        <div style="height: 1.25rem; margin-right: 0.75rem">
                                            <!-- 必修 -->
                                            <img v-show="item.studyType == '01'" class="img_icon_train" src="@/assets/img/train/icon_train_bx.png" />
                                            <!-- 选修 -->
                                            <img v-show="item.studyType == '02'" class="img_icon_train" src="@/assets/img/train/icon_train_xx.png" />
                                        </div>
                                        <!-- 有进度 -->
                                        <div style="height: 1.25rem">
                                            <!-- 已完成 写法 -->
                                            <img
                                                v-show="item.isCompleted == '01'"
                                                class="icon_train_ywc"
                                                src="@/assets/img/train/icon_train_ywc.png"
                                            />
                                            <!-- 未完成 写法 -->
                                            <div
                                                v-show="item.isCompleted == '02' && item.studyProgress > 0 && item.studyProgress < 100"
                                                class="train_process"
                                                :class="{
                                                    train_process_green: item.studyProgress >= 90,
                                                    train_process_yellow: item.studyProgress < 90,
                                                }"
                                            >
                                                <div
                                                    class="train_process_inner"
                                                    :class="{
                                                        train_process_inner_green: item.studyProgress >= 90,
                                                        train_process_inner_yellow: item.studyProgress < 90,
                                                    }"
                                                    :style="{ width: `${item.studyProgress}%` }"
                                                ></div>
                                                <div class="train_process_val" :class="{ train_process_val_green: item.studyProgress >= 90 }">
                                                    {{ item.studyProgress }}%
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="df_ac_jsb" style="margin-top: 0.75rem">
                                <div class="label_5">{{ item.subTitle }}</div>
                                <div style="display: flex">
                                    <img v-show="item.userIsLike == '是'" class="icon_like" src="@/assets/img/train/icon_like_light.png" />
                                    <img
                                        v-show="item.userIsLike == '否'"
                                        class="icon_like"
                                        @click="likeClick(item.id, item.fileId, $event)"
                                        src="@/assets/img/train/icon_like_dark.png"
                                    />
                                    <span v-show="item.userIsLike == '是'" class="text_2">{{ item.likeNum }}</span>
                                    <span v-show="item.userIsLike == '否'" class="text_1" @click="likeClick(item.id, item.fileId, $event)">{{
                                        item.likeNum
                                    }}</span>
                                    <img class="icon_split_1" src="@/assets/img/train/icon_split_1.png" />
                                    <img v-show="item.userIsComment == '是'" class="icon_comment" src="@/assets/img/train/icon_comment_light.png" />
                                    <img
                                        v-show="item.userIsComment == '否'"
                                        class="icon_comment"
                                        @click="commentClick(item.fileId, $event)"
                                        src="@/assets/img/train/icon_comment_dark.png"
                                    />
                                    <span v-show="item.userIsComment == '是'" class="text_2">{{ item.commentNum }}</span>
                                    <span v-show="item.userIsComment == '否'" class="text_1" @click="commentClick(item.fileId, $event)">{{
                                        item.commentNum
                                    }}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box_page">
                    <el-pagination
                        background
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="page.pageIndex"
                        layout="prev, pager, next"
                        :total="page.total"
                    >
                    </el-pagination>
                </div>
                <div>
                    <!-- 视频播放组件 -->
                    <videoPlayer
                        v-if="videoPlayerShow"
                        :videoId="videoId"
                        :title="title"
                        :show.sync="videoPlayerShow"
                        @updateFlag="updateFlag"
                    ></videoPlayer></div
            ></el-tab-pane>
            <el-tab-pane label="练习" name="second"
                ><div class="box_top">
                    <div class="t_row_1">
                        <div class="label_1" style="width: 65px">类型</div>
                        <div>
                            <el-checkbox-group v-model="trainTypeList">
                                <!-- <el-checkbox label="学习"></el-checkbox>
                        <el-checkbox label="考试"></el-checkbox> -->
                                <el-checkbox label="视频"></el-checkbox>
                                <el-checkbox label="文档"></el-checkbox>
                                <el-checkbox label="其他"></el-checkbox>
                            </el-checkbox-group>
                        </div>
                    </div>
                    <div class="t_row_2">
                        <div class="label_1 t_row_2_1">分类</div>
                        <div class="t_row_2_2">
                            <div class="class_item" :class="{ class_item_active: trainClass == '' }" @click="trainClassClick1('', '全部')">全部</div>
                            <div
                                class="class_item"
                                :class="{ class_item_active: trainClass == item.id }"
                                @click="trainClassClick1(item.id, item.major)"
                                v-for="(item, index) in trainClassList"
                                :key="index"
                            >
                                {{ item.major }}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box_bottom">
                    <div class="box_bottom_title">
                        <div style="display: flex; align-items: center">
                            <img id="icon_trainClass" src="@/assets/img/train/icon_trainClass.png" />
                            <span class="label_2">{{ trainClassName }}练习</span>
                        </div>
                        <el-input v-model="searchContent" @input="getTrain" style="width: 300px" placeholder="请输入标题关键字搜索"></el-input>
                        <!-- <div class="label_3">全部</div> -->
                    </div>
                    <div class="box_bottom_con">
                        <div class="con_card" @click="OnText(item)" v-for="(item, index) in trainData2" :key="index">
                            <!--实操-->
                            <img v-show="item.practicalOperation == '01'" class="icon_train_sc" src="@/assets/img/train/icon_train_sc.png" />
                            <div style="display: flex; height: 4.8125rem">
                                <div><img class="icon_card_zs" src="@/assets/img/train/icon_card_zs.png" /></div>
                                <div>
                                    <div class="label_4" style="height: 50%">{{ item.title }}</div>
                                    <div style="height: 50%; display: flex; align-items: flex-end">
                                        <div style="height: 1.25rem">
                                            <!-- 已完成 写法 -->
                                            <img
                                                v-show="item.isCompleted == '01'"
                                                class="icon_train_ywc"
                                                src="@/assets/img/train/icon_train_ywc.png"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box_page">
                    <el-pagination
                        background
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="page.pageIndex"
                        layout="prev, pager, next"
                        :total="page.total"
                    >
                    </el-pagination>
                </div>
            </el-tab-pane>
        </el-tabs>
        <!-- 继续答题ro重新答题 -->
        <el-dialog title="提示" :visible.sync="centerDialogVisible" width="30%" center>
            <span>是否继续答题</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="continueAnswer">继续答题</el-button>
                <el-button type="primary" @click="restartAnswer">重新答题</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import { debounce } from '@/utils/index';
    import videoPlayer from '@/views/videoPlayer/videoPlayer.vue';
    import trainApi from '@/api/train.js';
    export default {
        name: 'train',
        components: {
            videoPlayer,
        },
        watch: {
            trainTypeList(val) {
                if (val.length == 0) {
                    this.trainType = '';
                } else {
                    let arr = [];
                    val.forEach((item) => {
                        if (item == '视频') {
                            arr.push('01');
                        } else if (item == '文档') {
                            arr.push('02');
                        } else if (item == '其他') {
                            arr.push('03');
                        }
                    });
                    this.trainType = arr.join();
                }
                this.queryData();
            },
        },
        data() {
            return {
                activeName: 'first',
                searchContent: '',
                userNo: this.$store.getters.userInfo.userName,
                orgNo: this.$store.getters.userInfo.orgNo,
                // userNo: '40020',
                // orgNo: '62403',
                trainType: '',
                trainTypeList: [],
                trainClass: '',
                trainClassName: '全部',
                trainClassList: [],
                page: {
                    pageIndex: 1,
                    pageSize: 15,
                    total: 0,
                },
                //视频播放
                videoId: '',
                title: '',
                // src: 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4',
                videoPlayerShow: false,
                trainData: [],
                trainData2: [],
                centerDialogVisible: false,
                selectedPaper: '',
            };
        },
        mounted() {
            this.getMajorList();
            this.queryData();
            this.fetchData();
        },
        methods: {
            handleClick(tab, event) {
                console.log(tab, event);
            },
            getMajorList() {
                let params = { orgNo: this.orgNo };
                trainApi.getMajorList(params).then((data) => {
                    this.trainClassList = data.response;
                    console.log('专业', data.response);
                });
            },
            getTrain: debounce(function () {
                this.queryData();
            }, 500),
            queryData() {
                this.trainData = [];
                let params = {
                    userNo: this.userNo,
                    majorId: this.trainClass,
                    fileType: this.trainType,
                    pageIndex: this.page.pageIndex,
                    pageSize: this.page.pageSize,
                    title: this.searchContent,
                };
                trainApi.getTrainPageList(params).then((data) => {
                    const res = data.response;
                    this.trainData = res.list;
                    console.log('全部培训', this.trainData);
                    this.page.total = res.total;
                });
            },
            //练习
            fetchData() {
                this.trainData2 = [];
                console.log('调用方法----------');
                let params = {
                    userNo: this.userNo,
                    majorId: this.trainClass,
                    fileType: this.trainType,
                    pageIndex: this.page.pageIndex,
                    pageSize: this.page.pageSize,
                    title: this.searchContent,
                };
                trainApi
                    .successPageList(params)
                    .then((data) => {
                        console.log('培训学习', data.response.list);
                        this.trainData2 = data.response.list;
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            },
            play(videoId, title) {
                this.videoId = videoId;
                this.title = title;
                this.videoPlayerShow = true;
            },
            likeClick(id, videoId, event) {
                this.videoId = videoId;
                event.stopPropagation();
                let params = {
                    id: id,
                    fileId: this.videoId,
                    userNo: this.userNo,
                    operType: '01',
                };
                trainApi.saveLikeComment(params).then((res) => {
                    if (res.response == 1) {
                        this.queryData();
                    }
                });
            },
            commentClick(videoId, event) {
                this.videoId = videoId;
                event.stopPropagation();
            },
            trainClassClick(val1, val2) {
                this.trainClass = val1;
                this.trainClassName = val2;
                this.queryData();
            },
            trainClassClick1(val1, val2) {
                this.trainClass = val1;
                this.trainClassName = val2;
                this.fetchData();
            },
            handleSizeChange(val) {
                this.page.pageSize = val;
                this.page.pageIndex = 1;
                this.queryData();
            },
            handleCurrentChange(val) {
                this.page.pageIndex = val;
                this.queryData();
            },
            updateFlag(val) {
                console.log('更新标识', val);
                if (val) {
                    this.queryData();
                }
            },
            //试卷id
            OnText(item) {
                console.log('majorId', item.majorId, item.fileId);
                let params = {
                    fileId: item.fileId,
                    userNo: this.userNo,
                    majorId: item.majorId,
                    isCompleted: item.isCompleted,
                };
                const loading = this.$loading({
                    lock: true,
                    text: '正在组卷中',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)',
                });
                trainApi.getPractice(params).then((res) => {
                    console.log('getPractice-----' + res);
                    if (res.code === 1) {
                        console.log('查询出试卷的id', res.response);
                        loading.close();
                        this.selectedPaper = res.response.id;
                        this.centerDialogVisible = true;
                    } else {
                        this.$message.error(res.message);
                        loading.close();
                    }
                });
            },
            // 继续答题
            continueAnswer() {
                console.log('继续答题试卷id', this.selectedPaper);
                const id = this.selectedPaper;
                const { href } = this.$router.resolve({
                    name: 'ExamPaperReadPractice',
                    query: { id: id },
                });
                this.centerDialogVisible = false;
                window.open(href, '_blank');
            },
            //重新答题
            restartAnswer() {
                console.log('重新题试卷id', this.selectedPaper);
                const id = this.selectedPaper;
                const { href } = this.$router.resolve({
                    name: 'ExamPaperDoPractice',
                    query: { id: id },
                });
                this.centerDialogVisible = false;
                window.open(href, '_blank');
            },
        },
    };
</script>

<style lang="scss" scoped>
    /depp/.el-tabs__nav-wrap::after {
        background-color: #fff;
    }
    .df_ac_jsb {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .train_box {
        width: 1390px;
        height: 100%;
        padding-top: 30px;
        margin-left: 300px;
    }
    .box_top {
        width: 100%;
        height: 155px;
        background-color: #ffffff;
        border-radius: 6px;
        border: solid 1px #e7eaec;
        .t_row_1 {
            width: 100%;
            height: 50px;
            border-bottom: solid 1px #d0d5d7;
            padding: 0 25px;
            display: flex;
            align-items: center;
        }
        .t_row_2_1 {
            display: flex;
            align-items: center;
            height: 48px;
            padding: 0 25px;
        }
        .t_row_2_2 {
            display: flex;
            padding-left: 90px;
        }
        .class_item {
            width: 105px;
            height: 35px;
            font-size: 16px;
            color: #a9b1c4;
            text-shadow: 0 0 #a9b1c4;
            border-radius: 18px;
            border: solid 1px #a9b1c4;
            margin-right: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
        }
        .class_item_active {
            background-color: #1962f8;
            border: solid 1px #1962f8;
            color: #fff;
            text-shadow: 0 0 #fff;
        }
    }
    .label_1 {
        font-size: 16px;
        color: #1c2421;
        font-weight: 600;
    }
    .label_2 {
        font-size: 24px;
        color: #1c2227;
        text-shadow: 0 0 #1c2227;
    }
    .label_3 {
        font-size: 20px;
        color: #1962f8;
        text-shadow: 0 0 #1962f8;
        cursor: pointer;
    }
    .label_4 {
        font-size: 20px;
        color: #1c2227;
        text-shadow: 0 0 #1c2227;
    }
    .label_5 {
        font-size: 16px;
        color: #a9b1c4;
        text-shadow: 0 0 #a9b1c4;
        width: 60%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .text_1 {
        font-size: 16px;
        color: #a9b1c4;
        text-shadow: 0 0 #a9b1c4;
    }
    .text_2 {
        font-size: 16px;
        color: #1962f8;
        text-shadow: 0 0 #1962f8;
    }
    .box_bottom {
        width: 100%;
        height: 450px;
        background-color: #ffffff;
        border-radius: 6px;
        border: solid 1px #e7eaec;
        margin-top: 25px;
        .box_bottom_title {
            width: calc(100% - 20px);
            height: 72px;
            border-bottom: solid 1px #e7ebec;
            padding-left: 30px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        #icon_trainClass {
            width: 25px;
            height: 22px;
            margin-right: 20px;
        }
        .box_bottom_con {
            width: 100%;
            height: calc(100% - 73px);
            padding: 20px 0;
            overflow-y: auto;
        }
        .con_card {
            position: relative;
            float: left;
            width: calc((100% - 108px) / 3);
            height: 143px;
            background-color: #f9fbfc;
            border-radius: 6px;
            border: solid 1px #e7eaec;
            padding: 15px 18px 20px 13px;
            margin: 0 18px 25px 18px;
            cursor: pointer;
        }
        .icon_card_zs {
            width: 135px;
            height: 77px;
            margin-right: 15px;
        }
        .img_icon_train {
            width: 50px;
            height: 21px;
        }
        .icon_train_ywc {
            width: 72px;
            height: 21px;
        }
        .train_process {
            position: relative;
            width: 72px;
            height: 21px;
            border-radius: 4px;
        }
        .train_process_green {
            border: solid 1px #00bc04;
        }
        .train_process_yellow {
            border: solid 1px #ffba00;
        }
        .train_process_inner {
            width: 0;
            height: 19px;
            border-radius: 4px;
        }
        .train_process_inner_green {
            background-color: #d3f7d9;
        }
        .train_process_inner_yellow {
            background-color: #ffba00;
        }
        .train_process_val {
            position: absolute;
            height: 19px;
            line-height: 19px;
            top: 0;
            right: 2px;
            font-size: 13px;
            color: #1c2227;
        }
        .train_process_val_green {
            color: #00bc04;
        }
        .icon_like {
            width: 15px;
            height: 15px;
            cursor: pointer;
            margin-right: 5px;
        }
        .icon_split_1 {
            width: 1px;
            height: 16px;
            margin: 0 10px;
        }
        .icon_comment {
            width: 18px;
            height: 17px;
            cursor: pointer;
            margin-right: 5px;
        }
        .icon_train_sc {
            position: absolute;
            top: 0;
            right: 0;
            width: 71px;
            height: 71px;
        }
    }
    .box_page {
        height: 36px;
        margin-top: 20px;
        text-align: right;
    }
    /deep/ .box_page .el-pager li {
        width: 36px;
        height: 36px;
        line-height: 36px;
        color: #707173;
        border: solid 1px #a9b1c4;
        background-color: #f9fbfc;
        font-size: 19px;
        font-weight: normal;
    }
    /deep/ .box_page .el-pagination .btn-prev {
        width: 36px;
        height: 36px;
        color: #333;
        border: solid 1px #a9b1c4;
        background-color: #f9fbfc;
    }
    /deep/ .box_page .el-pagination .btn-next {
        width: 36px;
        height: 36px;
        color: #333;
        border: solid 1px #a9b1c4;
        background-color: #f9fbfc;
    }
    /deep/ .box_page .el-pagination .btn-prev .el-icon {
        font-size: 16px;
    }
    /deep/ .box_page .el-pagination .btn-next .el-icon {
        font-size: 16px;
    }
    /deep/ .box_page .el-pagination.is-background .el-pager li:not(.disabled).active {
        color: #1962f8;
        background-color: #e2ebfc;
        border: solid 1px #1962f8;
    }

    /deep/ .box_top .el-checkbox {
        margin-right: 50px;
    }
    /deep/ .box_top .el-checkbox__label {
        font-size: 16px;
        color: #1c2421;
        font-weight: 600;
    }
    /deep/ .box_top .el-checkbox__inner {
        width: 19px;
        height: 19px;
        background-color: #f9fbfc;
        border-radius: 3px;
        border: solid 1px #d0d5d7;
    }
    /deep/ .box_top .el-checkbox__inner::after {
        width: 0.5rem;
        height: 0.75rem;
        border: 1px solid #a9b1c4;
        border-left: 0;
        border-top: 0;
        top: -1px;
        left: 0.25rem;
    }
    /deep/ .box_top .el-checkbox__input.is-checked + .el-checkbox__label {
        color: #a9b1c4;
    }
</style>
