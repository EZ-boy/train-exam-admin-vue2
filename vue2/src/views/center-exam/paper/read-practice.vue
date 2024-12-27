<template>
    <div class="goTop">
        <!-- <el-row class="do-exam-title-hidden">
            <el-col :span="24">
                <span :key="item.itemOrder" v-for="item in answer.answerItems">
                    <el-tag class="do-exam-title-tag">{{ item.itemOrder }}</el-tag>
                </span>
            </el-col>
        </el-row> -->
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
        <!-- <el-button @click="getpage">123</el-button> -->
        <div style="width: 100%" id="empty"></div>
        <el-main>
            <el-form :model="form" ref="form" v-loading="formLoading" label-width="100px">
                <el-row :key="index" v-for="(titleItem, index) in form.paper.titleItems">
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
                                :sr="answer.answerItems[questionItem.itemOrder - 1].showResult"
                                :answer="
                                    answer.answerItems.filter((item) => item.itemOrder == questionItem.itemOrder).length > 0
                                        ? answer.answerItems.filter((item) => item.itemOrder == questionItem.itemOrder)[0]
                                        : answer1
                                "
                                :textid="textid"
                            />
                        </el-form-item>
                    </el-card>
                </el-row>
            </el-form>
        </el-main>
        <!-- <div class="aaa">
            <el-button type="primary" @click="save">保存</el-button>
        </div> -->
    </div>
</template>

<script>
    import { mapState, mapGetters } from 'vuex';
    import { formatSeconds } from '@/utils';
    import QuestionEditPractice from '../components/QuestionEditPractice.vue';

    import examPaperAnswerApi from '@/api/centerexamPaperAnswer';
    export default {
        components: { QuestionEditPractice },
        data() {
            return {
                form: {
                    paper: {
                        titleItems: [],
                    },
                },
                formLoading: false,
                answer: {
                    id: null,
                    score: 0,
                    doTime: 0,
                    answerItems: [],
                    doRight: false,
                },
                userId: this.$store.getters.userInfo,
                answerRecord: '',
                list: [],
                answer1: {
                    content: null,
                    contentArray: null,
                    completed: false,
                },
            };
        },
        created() {
            this.textid = this.$route.query.id;
            let id = this.$route.query.id;

            let _this = this;
            let params = {
                userId: this.userId.id,
                paperId: id,
            };
            if (id && parseInt(id) !== 0) {
                _this.formLoading = true;
                examPaperAnswerApi.getByPidUid(params).then((re) => {
                    _this.answerRecord = re.response;
                    console.log('=========answerRecord============');
                    console.log(_this.answerRecord);
                    examPaperAnswerApi.read(_this.answerRecord).then((re) => {
                        console.log('=========form============');
                        console.log(re.response);
                        _this.form = re.response;
                        _this.answer = re.response.answer;
                        _this.answer.answerItems.map((item) => {
                            if ((item.content != null && item.content != '') || (item.contentArray != null && item.contentArray.length > 0)) {
                                _this.$set(item, 'completed', true);
                                _this.$set(item, 'showResult', true);
                            } else {
                                _this.$set(item, 'completed', false);
                                _this.$set(item, 'showResult', false);
                            }
                        });
                        let arr = [];
                        this.form.paper.titleItems.map((it) => {
                            it.questionItems.map((item) => {
                                let a = this.answer.answerItems.filter((ai) => {
                                    return item.itemOrder === ai.itemOrder;
                                });
                                if (a.length > 0) {
                                    arr.push(a[0]);
                                } else {
                                    arr.push({
                                        itemOrder: item.itemOrder,
                                        questionId: item.id,
                                        content: null,
                                        contentArray: null,
                                        completed: false,
                                        showResult: false,
                                    });
                                }
                            });
                            // for (let i = 0; i < this.answer.answerItems.length; i++) {
                            //     let a = it.questionItems.filter((item) => {
                            //         return item.itemOrder === this.answer.answerItems[i].itemOrder;
                            //     });
                            //     if (a.length > 0) {
                            //         arr = [...a];
                            //     }
                            // }
                        });
                        this.answer.answerItems = arr;
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
                });
            }
        },
        computed: {
            /* answer1: {
                    get() {
                        let arr = [];
                        this.form.paper.titleItems.map((it) => {
                            it.questionItems.map((item) => {
                                for (let i = 0; i < this.answer.answerItems.length; i++) {
                                    if (item.itemOrder === this.answer.answerItems[i].itemOrder) {
                                        arr.push(this.answer.answerItems[i]);
                                    } else {
                                        let obj = {
                                            questionId: item.id,
                                            content: null,
                                            contentArray: null,
                                        };
                                        arr.push(obj);
                                    }
                                }
                            });
                        });
                        let a = {
                            id: null,
                            score: 0,
                            doTime: 0,
                            answerItems: arr,
                            doRight: false,
                        };
                        return a;
                    },
                }, */
        },
        methods: {
            srFun(qitem) {
                let flag = false;
                for (let i = 0; i < this.answer.answerItems.length; i++) {
                    if (qitem.itemOrder === this.answer.answerItems[i].itemOrder) {
                        flag = true;
                    }
                }
                return flag;
            },
            questionCompleted(completed) {
                return this.enumFormat(this.$store.state.enumItem.exam.answer.doCompletedTag, completed);
            },
            getpage() {
                let id = this.$route.query.id;
                examPaperAnswerApi.read(id).then((re) => {
                    console.log('调用试卷接口', re.response.paper);
                });
            },
            save() {
                this.submitForm(this.list);
            },
            changeAnswer(list) {
                this.list = list;
            },
            //提交
            submitForm(answer) {
                let _this = this;
                examPaperAnswerApi
                    .answerSubmit(answer)
                    .then((re) => {
                        if (re.code === 1) {
                            console.log(re.response);
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
            questionDoRightTag(status) {
                return this.enumFormat(this.$store.state.enumItem.exam.answer.doRightTag, status);
            },
            goAnchor(selector) {
                this.$el.querySelector(selector).scrollIntoView({ behavior: 'instant', block: 'center', inline: 'nearest' });
            },
        },
        computed: {
            ...mapGetters('enumItem', ['enumFormat']),
            // ...mapState('enumItem', {
            //   doRightTag: state => state.exam.question.answer.doRightTag
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
    .goTop {
        height: 100vh;
        overflow-x: hidden;
    }
    .do-exam-title {
        position: fixed;
        z-index: 10;
        padding: 1rem 5rem;
        background-color: whitesmoke;
        width: 100%;
    }
    .do-exam-title-tag {
        cursor: pointer;
    }
    /deep/ .el-form-item--medium .el-form-item__label {
        line-height: 1.5rem;
    }
    /deep/ .el-main {
        padding: 1.25rem 5rem;
        overflow-y: auto;
    }
    /deep/ .el-tag.el-tag--success {
        color: #fff;
        background-color: #13ce66;
    }
    /deep/ .el-tag.el-tag--danger {
        background-color: #ff4949;
        color: #fff;
    }
    .aaa {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 40px;
    }
</style>
