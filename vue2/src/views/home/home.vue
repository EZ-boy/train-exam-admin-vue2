<template>
    <div id="home_box">
        <div class="home_top_box">
            <div><img id="icon_top_title" src="@/assets/img/home/icon_top_title.png" /></div>
            <div><img id="icon_top_subtitle" src="@/assets/img/home/icon_top_subtitle.png" /></div>
        </div>
        <div class="home_bottom_box">
            <div class="box_bottom_title">
                <div style="display: flex; align-items: center">
                    <img id="icon_label" src="@/assets/img/home/icon_label.png" />
                    <span class="label_2">培训总体情况</span>
                </div>
                <!-- <div class="label_3">全部</div> -->
            </div>
            <div class="box_bottom_con">
                <div class="con_item" v-for="(item, index) in trainList" :key="index">
                    <div v-if="index < 3"><img class="icon_top" :src="require(`@/assets/img/home/icon_top_${index + 1}.png`)" /></div>
                    <div v-else><img class="icon_top" src="@/assets/img/home/icon_top_4.png" /></div>
                    <div v-if="index < 3" :class="['top_num', `top_num_${index + 1}`]">{{ index + 1 }}</div>
                    <div v-else class="top_num top_num_4">{{ index + 1 }}</div>
                    <div class="con_item_left">
                        <div><img class="icon_row_zs" src="@/assets/img/home/icon_row_zs.png" /></div>
                        <div class="label_1" style="margin-top: 0.25rem; margin-bottom: 0.5rem">{{ item.org_name }}</div>
                        <div>
                            <span class="label_4">总进度：</span
                            ><span class="label_3" style="cursor: default"
                                >{{ ((item.study_finished + item.exam_finished) * 100) / 2 / item.tasking_cnt }}%</span
                            >
                        </div>
                    </div>
                    <div class="con_item_right">
                        <div style="display: flex; align-items: center">
                            <div style="display: flex"><img class="icon_p_xx" src="@/assets/img/home/icon_p_xx.png" /></div>
                            <div>
                                <span class="label_1">学习进度:</span
                                ><span class="label_1">{{ (item.study_finished * 100) / item.tasking_cnt }}%</span>
                            </div>
                            <div class="con_p_box">
                                <div class="con_p_inner" :style="{ width: `${(item.study_finished * 100) / item.tasking_cnt}%` }"></div>
                            </div>
                            <div style="display: flex"><img class="icon_p_ks" src="@/assets/img/home/icon_p_ks.png" /></div>
                            <div>
                                <span class="label_1">考试进度:</span
                                ><span class="label_1">{{ (item.exam_finished * 100) / item.tasking_cnt }}%</span>
                            </div>
                            <div class="con_p_box">
                                <div class="con_p_inner" :style="{ width: `${(item.exam_finished * 100) / item.tasking_cnt}%` }"></div>
                            </div>
                        </div>
                        <div class="con_train_list">
                            <div class="con_train_list_item" v-for="(innerItem, innerIndex) in item.zyjdList" :key="innerIndex">
                                <span class="label_5">{{ innerItem.major_name }}：</span>
                                <span class="label_1">{{ innerItem.exam_major_finished }}</span>
                                <span class="label_4">/</span>
                                <span class="label_4">{{ innerItem.tasking_major_cnt }}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import homeApi from '@/api/home';
    export default {
        data() {
            return {
                trainList: [
                    {
                        org_name: '天水供电公司',
                        tasking_cnt: 87,
                        exam_finished: 68,
                        study_finished: 76,
                        zyjdList: [{ major_name: '业扩专业完成进度', exam_major_finished: 20, tasking_major_cnt: 25 }],
                    },
                ],
            };
        },
        mounted() {
            this.qryTrainList();
        },
        methods: {
            qryTrainList() {
                homeApi.studyExamInfoByUser().then((data) => {
                    console.log(data.response);
                    this.trainList = data.response;
                });
            },
        },
    };
</script>

<style lang="scss" scoped>
    #home_box {
        width: 100%;
        height: 100%;
        overflow-y: auto;
    }
    .home_top_box {
        width: 100%;
        height: 455px;
        background-image: url('../../assets/img/home/icon_top_bj.png');
        background-repeat: no-repeat;
        background-size: 100% 100%;
        #icon_top_title {
            width: 848px;
            height: 80px;
            margin: 110px 0 0 300px;
        }
        #icon_top_subtitle {
            width: 446px;
            height: 33px;
            margin: 35px 0 0 300px;
        }
    }
    .home_bottom_box {
        width: 1390px;
        margin: 60px 0 55px 300px;
        background-color: #ffffff;
        border-radius: 6px;
        border: solid 1px #e7eaec;
    }
    .box_bottom_title {
        width: 100%;
        height: 72px;
        border-bottom: solid 1px #e7ebec;
        padding-left: 40px;
        padding-right: 35px;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    #icon_label {
        width: 24px;
        height: 24px;
        margin-right: 16px;
    }
    .label_1 {
        font-size: 20px;
        color: #1c2227;
        text-shadow: 0 0 #1c2227;
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
        color: #8e939f;
        text-shadow: 0 0 #8e939f;
    }
    .label_5 {
        font-size: 16px;
        color: #8e939f;
        text-shadow: 0 0 #8e939f;
    }
    .box_bottom_con {
        width: 100%;
        padding: 25px 35px 0 35px;
    }
    .con_item {
        position: relative;
        width: 100%;
        background-color: #ffffff;
        border-radius: 6px;
        border: solid 1px #e7eaec;
        margin-bottom: 20px;
        padding: 20px 30px;
        display: flex;
    }
    .con_item_left {
        width: 125px;
    }
    .con_item_right {
        width: calc(100% - 125px);
        padding-top: 5px;
    }
    .icon_row_zs {
        width: 121px;
        height: 77px;
    }
    .icon_p_xx {
        width: 20px;
        height: 19px;
        margin-left: 65px;
        margin-right: 10px;
    }
    .icon_p_ks {
        width: 19px;
        height: 21px;
        margin-left: 65px;
        margin-right: 10px;
    }
    .con_p_box {
        width: 190px;
        height: 5px;
        background-color: #a9b1c4;
        border-radius: 2px;
        margin-left: 20px;
    }
    .con_p_inner {
        width: 0;
        height: 100%;
        background-color: #1962f8;
        border-radius: 2px;
    }
    .con_train_list {
        display: flex;
        width: 100%;
        padding: 0 65px;
        margin-top: 30px;
        flex-flow: wrap;
    }
    .con_train_list_item {
        display: flex;
        align-items: center;
        margin-right: 50px;
        margin-bottom: 20px;
        height: 20px;
        line-height: 1;
    }
    .icon_top {
        position: absolute;
        top: 0;
        right: 0;
        width: 71px;
        height: 71px;
    }
    .top_num {
        position: absolute;
        top: 71px;
        right: 30px;
        font-size: 45px;
        font-weight: bold;
    }
    .top_num_1 {
        color: #ff2a00;
    }
    .top_num_2 {
        color: #e49600;
    }
    .top_num_3 {
        color: #00c525;
    }
    .top_num_4 {
        color: #1962f8;
    }
</style>
