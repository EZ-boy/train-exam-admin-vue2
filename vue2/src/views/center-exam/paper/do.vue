<template>
    <div class="goTop">
        <el-backtop target=".goTop"></el-backtop>
        <el-row class="do-exam-title">
            <el-col :span="24">
                <span :key="item.itemOrder" v-for="item in answer.answerItems">
                    <el-tag :type="questionCompleted(item.completed)" class="do-exam-title-tag" @click="goAnchor('#question-' + item.itemOrder)">{{
                        item.itemOrder
                    }}</el-tag>
                </span>
                <div class="do-exam-time">
                    <label>剩余时间：</label>
                    <label>{{ formatSeconds(remainTime) }}</label>
                </div>
            </el-col>
        </el-row>
        <el-container class="app-item-contain" style="padding-top: 100px">
            <el-header class="align-center">
                <h1>{{ form.name }}</h1>
                <div>
                    <span class="question-title-padding">试卷总分：{{ form.score }}</span>
                    <span class="question-title-padding">考试时间：{{ form.suggestTime }}分钟</span>
                </div>
            </el-header>
            <el-main>
                <el-form :model="form" ref="form" v-loading="formLoading" label-width="100px">
                    <el-row :key="index" v-for="(titleItem, index) in form.titleItems">
                        <h3>{{ titleItem.name }}</h3>
                        <el-card class="exampaper-item-box" v-if="titleItem.questionItems.length !== 0">
                            <el-form-item
                                :key="questionItem.itemOrder"
                                :label="questionItem.itemOrder + '.'"
                                v-for="questionItem in titleItem.questionItems"
                                class="exam-question-item"
                                label-width="50px"
                                :id="'question-' + questionItem.itemOrder"
                            >
                                <QuestionEdit
                                    :qType="questionItem.questionType"
                                    :question="questionItem"
                                    :answer="answer.answerItems[questionItem.itemOrder - 1]"
                                />
                            </el-form-item>
                        </el-card>
                    </el-row>
                    <el-row class="do-align-center">
                        <el-button type="primary" @click="submitForm">提交</el-button>
                        <el-button @click="backCenter">取消</el-button>
                    </el-row>
                </el-form>
            </el-main>
        </el-container>
    </div>
</template>

<script>
    import { mapState, mapGetters } from 'vuex';
    import { formatSeconds } from '@/utils';
    import QuestionEdit from '../components/QuestionEdit';
    import examPaperApi from '@/api/centerexamPaper';
    import examPaperAnswerApi from '@/api/centerexamPaperAnswer';

    export default {
        components: { QuestionEdit },
        data() {
            return {
                form: {},
                formLoading: false,
                answer: {
                    questionId: null,
                    doTime: 0,
                    answerItems: [],
                },
                timer: null,
                remainTime: 0,
            };
        },
        created() {
            let id = this.$route.query.id;
            let _this = this;
            if (id && parseInt(id) !== 0) {
                _this.formLoading = true;
                examPaperApi.select(id).then((re) => {
                    _this.form = re.response;
                    _this.remainTime = re.response.suggestTime * 60;
                    _this.initAnswer();
                    _this.timeReduce();
                    _this.formLoading = false;
                });
            }
        },
        mounted() {
            // console.log(this.doCompletedTag);
            // console.log(this.$store.state);
        },
        beforeDestroy() {
            window.clearInterval(this.timer);
        },
        methods: {
            formatSeconds(theTime) {
                return formatSeconds(theTime);
            },
            timeReduce() {
                let _this = this;
                this.timer = setInterval(function () {
                    if (_this.remainTime <= 0) {
                        _this.submitForm();
                    } else {
                        ++_this.answer.doTime;
                        --_this.remainTime;
                    }
                }, 1000);
            },
            questionCompleted(completed) {
                return this.enumFormat(this.$store.state.enumItem.exam.answer.doCompletedTag, completed);
            },
            goAnchor(selector) {
                this.$el.querySelector(selector).scrollIntoView({ behavior: 'instant', block: 'center', inline: 'nearest' });
            },
            initAnswer() {
                this.answer.id = this.form.id;
                let titleItemArray = this.form.titleItems;
                for (let tIndex in titleItemArray) {
                    let questionArray = titleItemArray[tIndex].questionItems;
                    for (let qIndex in questionArray) {
                        let question = questionArray[qIndex];
                        this.answer.answerItems.push({
                            questionId: question.id,
                            content: null,
                            contentArray: [],
                            completed: false,
                            itemOrder: question.itemOrder,
                        });
                    }
                }
            },
            submitForm() {
                let _this = this;
                window.clearInterval(_this.timer);
                _this.formLoading = true;
                console.log('提交试卷', this.answer);
                examPaperAnswerApi
                    .answerSubmit(this.answer)
                    .then((re) => {
                        if (re.code === 1) {
                            _this.$alert('试卷得分：' + re.response + '分', '考试结果', {
                                confirmButtonText: '返回考试记录',
                                callback: (action) => {
                                    _this.$router.go(-1);
                                },
                            });
                        } else {
                            _this.$message.error(re.message);
                        }
                        _this.formLoading = false;
                    })
                    .catch((e) => {
                        _this.formLoading = false;
                    });
            },
            backCenter() {
                this.$router.go(-1);
            },
        },
        computed: {
            ...mapGetters('enumItem', ['enumFormat']),
            // ...mapState('enumItem', {
            //   doCompletedTag: state => state.enumItem.exam.answer.doCompletedTag
            // })
        },
    };
</script>

<style lang="scss" scoped>
    .align-center {
        text-align: center;
    }

    .exam-question-item {
        padding: 10px;

        .el-form-item__label {
            font-size: 15px !important;
        }
    }

    .question-title-padding {
        padding-left: 25px;
        padding-right: 25px;
    }
    .do-align-center {
        margin-top: 30px;
        /* margin: auto; */
        display: flex;
        justify-content: center;
    }
    .do-exam-title {
        position: fixed;
        z-index: 10;
        padding: 1rem 5rem;
        background-color: whitesmoke;
        width: 100%;
    }
    .goTop {
        height: 100vh;
        overflow-x: hidden;
    }
    .do-exam-title-tag {
        cursor: pointer;
    }
    /deep/ .el-form-item--medium .el-form-item__label {
        line-height: 1.5rem;
    }
    /deep/ .el-main {
        padding: 1.25rem 5rem;
    }
    /deep/ .el-tag.el-tag--success {
        color: #fff;
        background-color: #13ce66;
    }
</style>
