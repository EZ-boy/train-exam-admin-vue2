<template>
    <div class="goTop">
        <el-backtop target=".goTop"></el-backtop>
        <el-row id="examTitle" class="do-exam-title">
            <el-col :span="24">
                <span :key="item.itemOrder" v-for="item in answer.answerItems">
                    <el-tag :type="questionCompleted(item.completed)" class="do-exam-title-tag" @click="goAnchor('#question-' + item.itemOrder)">{{
                        item.itemOrder
                    }}</el-tag>
                </span>
            </el-col>
        </el-row>
        <div style="width: 100%" id="empty"></div>

        <el-container class="app-item-contain">
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
                                <QuestionEditPractice
                                    :qType="questionItem.questionType"
                                    :question="questionItem"
                                    :answer="answer.answerItems[questionItem.itemOrder - 1]"
                                    :textid="textid"
                                />
                            </el-form-item>
                        </el-card>
                    </el-row>
                    <!-- <el-row class="do-align-center">
                        <el-button type="primary" @click="submitForm">提交</el-button>
                        <el-button @click="backCenter">取消</el-button>
                    </el-row> -->
                </el-form>
            </el-main>
        </el-container>
        <!-- <div class="aaa">
            <el-button type="primary" @click="save">保存</el-button>
        </div> -->
    </div>
</template>

<script>
    import { mapState, mapGetters } from 'vuex';
    import { formatSeconds } from '@/utils';
    import QuestionEditPractice from '../components/QuestionEditPractice.vue';
    import examPaperApi from '@/api/centerexamPaper';
    import examPaperAnswerApi from '@/api/centerexamPaperAnswer';

    export default {
        components: { QuestionEditPractice },
        data() {
            return {
                form: {
                    titleItems: [],
                },
                formLoading: false,
                answer: {
                    questionId: null,
                    doTime: 0,
                    answerItems: [],
                },
                timer: null,
                remainTime: 0,
                answerResult: 0,
                textid: 0,
                userInfo: this.$store.getters.userInfo,
            };
        },
        created() {
            this.textid = this.$route.query.id;
            console.log('试卷id', this.textid);
            let _this = this;
            if (_this.textid && parseInt(_this.textid) !== 0) {
                _this.formLoading = true;
                let params = {
                    userId: this.userInfo.id,
                    paperId: _this.textid,
                };
                examPaperApi.reDoPractice(params).then((re) => {
                    _this.form = re.response;
                    _this.initAnswer();
                    _this.formLoading = false;
                    this.$nextTick(() => {
                        let h = document.getElementById('examTitle').offsetHeight;
                        document.getElementById('empty').style.height = h + 'px';
                        let a = document.body.offsetHeight - h;
                        let list = document.getElementsByClassName('el-main');
                        for (let i = 0; i < list.length; i++) {
                            list[i].style.height = a - 40 + 'px';
                        }
                    });
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
            save() {
                this.submitForm(this.list);
            },
            /* changeAnswer(list) {
                this.list = list;
            }, */
            //提交
            submitForm(answer) {
                let _this = this;
                examPaperAnswerApi
                    .answerSubmit(this.answer)
                    .then((re) => {
                        if (re.code === 1) {
                            console.log(re.response);
                            this.$message.success('保存成功');
                        } else {
                            _this.$message.error(re.message);
                        }
                        _this.formLoading = false;
                    })
                    .catch((e) => {
                        _this.formLoading = false;
                    });
            },
            formatSeconds(theTime) {
                return formatSeconds(theTime);
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
                //this.submitForm();
            },
            /* submitForm() {
                let _this = this;
                window.clearInterval(_this.timer);
                _this.formLoading = true;
                examPaperAnswerApi
                    .answerSubmit(this.answer)
                    .then((re) => {
                        if (re.code === 1) {
                            console.log(re.response);
                            _this.answerResult = re.response;
                        } else {
                            _this.$message.error(re.message);
                        }
                        _this.formLoading = false;
                    })
                    .catch((e) => {
                        _this.formLoading = false;
                    });
            }, */
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
    .aaa {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 40px;
    }
</style>
