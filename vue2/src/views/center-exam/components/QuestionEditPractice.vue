<template>
    <div style="line-height: 1.8">
        <div v-if="qType == 1" v-loading="qLoading">
            <div class="q-title" v-html="question.title" />
            <div class="q-content">
                <el-radio-group v-model="answer.content" @change="answer.completed = true">
                    <el-radio
                        v-for="item in question.items"
                        :key="item.prefix"
                        :label="item.prefix"
                        @change="handleAnswerClick(question, item)"
                        :disabled="answer.completed"
                    >
                        <span class="question-prefix">{{ item.prefix }}.</span>
                        <span v-html="item.content" class="q-item-span-content"></span>
                    </el-radio>
                </el-radio-group>
                <div v-if="sr || showResult">
                    <div class="question-answer-show-item" style="margin-top: 15px">
                        <span class="question-show-item">结果：</span>
                        <el-tag :type="isCorrect ? 'success' : 'danger'">
                            {{ isCorrect ? '正确' : '错误' }}
                        </el-tag>
                    </div>
                    <div class="question-answer-show-item">
                        <span class="question-show-item">难度：</span>
                        <el-rate disabled v-model="question.difficult" class="question-show-item"></el-rate>
                    </div>
                    <br />
                    <div class="question-answer-show-item" style="line-height: 1.8">
                        <span class="question-show-item">解析：</span>
                        <span v-html="question.analyze" class="q-item-span-content" />
                    </div>
                    <div class="question-answer-show-item">
                        <span class="question-show-item">正确答案：</span>
                        <span v-if="qType == 1 || qType == 2 || qType == 5" v-html="question.correct" class="q-item-span-content" />
                        <span v-if="qType == 3" v-html="trueFalseFormatter(question)" class="q-item-span-content" />
                        <span v-if="qType == 4">{{ question.correctArray }}</span>
                    </div>
                </div>
            </div>
        </div>
        <div v-else-if="qType == 2" v-loading="qLoading">
            <div class="q-title" v-html="question.title" />
            <div class="q-content">
                <el-checkbox-group v-model="answer.contentArray" @change="answer.completed = true" :disabled="buttonClicked || sr">
                    <el-checkbox v-for="item in question.items" :label="item.prefix" :key="item.prefix">
                        <span class="question-prefix">{{ item.prefix }}.</span>
                        <span v-html="item.content" class="q-item-span-content"></span>
                    </el-checkbox>
                </el-checkbox-group>
                <el-button @click="confirmAnswer(question, answer)" type="primary" plain size="mini" :disabled="buttonClicked || sr">确定</el-button>
            </div>
            <div v-if="sr || showResult">
                <div class="question-answer-show-item" style="margin-top: 15px">
                    <span class="question-show-item">结果：</span>
                    <el-tag :type="isCorrect ? 'success' : 'danger'">
                        {{ isCorrect ? '正确' : '错误' }}
                    </el-tag>
                </div>
                <div class="question-answer-show-item">
                    <span class="question-show-item">难度：</span>
                    <el-rate disabled v-model="question.difficult" class="question-show-item"></el-rate>
                </div>
                <br />
                <div class="question-answer-show-item" style="line-height: 1.8">
                    <span class="question-show-item">解析：</span>
                    <span v-html="question.analyze" class="q-item-span-content" />
                </div>
                <div class="question-answer-show-item">
                    <span class="question-show-item">正确答案：</span>
                    <span v-if="qType == 1 || qType == 2 || qType == 5" v-html="question.correct" class="q-item-span-content" />
                    <span v-if="qType == 3" v-html="trueFalseFormatter(question)" class="q-item-span-content" />
                    <span v-if="qType == 4">{{ question.correctArray }}</span>
                </div>
            </div>
        </div>
        <div v-else></div>
    </div>
</template>

<script>
    import examPaperAnswerApi from '@/api/centerexamPaperAnswer';
    import { mapState, mapGetters } from 'vuex';
    export default {
        name: 'QuestionShow',
        props: {
            question: {
                type: Object,
                default: function () {
                    return {};
                },
            },
            sr: {
                type: Boolean,
                default: false,
            },
            answer: {
                type: Object,
                default: function () {
                    return { id: null, content: '', contentArray: [] };
                },
            },
            qLoading: {
                type: Boolean,
                default: false,
            },
            qType: {
                type: Number,
                default: 0,
            },
            textid: {
                type: String,
                default: '',
            },
        },
        data() {
            return {
                buttonClicked: false,
                showResult: false,
                isCorrect: this.answer.doRight,
                userAnswer: '',
                correctAnswer: '',
                answerSubmit: {
                    questionId: null,
                    doTime: 0,
                    answerItems: [],
                    id: null,
                    isPractice: '01',
                },
            };
        },
        mounted() {
            console.log(this.sr);
        },
        methods: {
            //多选题点击事件
            confirmAnswer(question, answer) {
                this.buttonClicked = true;
                this.handleAnswerClick(question, answer);
            },
            //获取用户答案事件
            handleAnswerClick(question, item) {
                console.log('答案:', question, item);
                console.log('试卷id-----', this.textid);

                if (this.qType === 2) {
                    // 多选题
                    this.handleMultipleChoice(question, item);
                } else if (this.qType === 1) {
                    // 单选题
                    this.handleSingleChoice(question, item);
                }
                this.answerSubmit.id = this.textid;
                this.answerSubmit.questionId = question.id;
                this.answerSubmit.answerItems.push({
                    questionId: question.id,
                    content: item.prefix,
                    contentArray: item.contentArray,
                    completed: true,
                    itemOrder: question.itemOrder,
                });
                this.$emit('change', this.answerSubmit);
                this.submitForm(this.answerSubmit);
            },
            handleSingleChoice(question, item) {
                this.showResult = true; // 显示结果
                if (question.correct === item.prefix) {
                    this.isCorrect = true; // 正确
                } else {
                    this.isCorrect = false; // 错误
                    this.userAnswer = item.prefix;
                    this.correctAnswer = question.correct;
                }
            },
            handleMultipleChoice(question, item) {
                this.showResult = true; // 显示结果
                this.userAnswer = item.contentArray.sort().join(',');
                if (question.correct === this.userAnswer) {
                    this.isCorrect = true; // 正确
                } else {
                    this.isCorrect = false; // 错误;
                    this.correctAnswer = question.correct;
                }
            },
            trueFalseFormatter(question) {
                return question.items.filter((d) => d.prefix === question.correct)[0].content;
            },
            doRightTagFormatter(status) {
                return this.enumFormat(this.$store.state.enumItem.exam.answer.doRightTag, status);
            },
            doRightTextFormatter(status) {
                return this.enumFormat(this.$store.state.enumItem.exam.answer.doRightEnum, status);
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
        },
        computed: {
            ...mapGetters('enumItem', ['enumFormat']),
            // ...mapState('enumItem', {
            //   doRightEnum: state => state.exam.question.answer.doRightEnum,
            //   doRightTag: state => state.exam.question.answer.doRightTag
            // })
        },
    };
</script>
