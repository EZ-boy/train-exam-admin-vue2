<template>
    <div class="box">
        <!-- input输入框 -->
        <div class="box-left" v-if="type == 'input'">
            <!-- <span v-if="!state">{{ keyword }}</span> -->
            <el-input ref="inp" size="medium" @click.stop v-model="keyword" @blur="submit"></el-input>
        </div>
        <!-- select下拉框 -->
        <div class="box-left" v-if="type == 'select'">
            <span v-if="!state">{{ option2val }}</span>
            <el-select size="medium" v-else v-model="keyword">
                <el-option v-for="(item, inx) in options" :key="inx" :label="item.label" :value="item.value"> </el-option>
            </el-select>
        </div>
        <!-- 右边的按钮操作 -->
        <!-- <div class="box-right">
            <i class="el-icon-edit" v-if="!state" @click.stop="focus"></i>
            <i class="el-icon-check" v-if="state" @click.stop="submit"></i>
        </div> -->
    </div>
</template>

<script>
    export default {
        props: {
            // 参数
            value: {},
            // 类型
            type: {
                type: String,
            },
            // 如果是select下拉
            options: {
                type: Array,
                default: () => [],
            },
        },
        data() {
            return {
                keyword: this.value,
                state: false,
            };
        },
        computed: {
            // 下拉框返回的值需要处理一下
            option2val() {
                const res = this.options.find((item) => item.value == this.keyword);
                return res && res.label ? res.label : '';
            },
        },
        methods: {
            focus() {
                this.state = true;
                if (this.type == 'input') {
                    //   要等dom先变化后才能拿到ref
                    this.$nextTick(() => {
                        this.$refs['inp'].focus();
                    });
                }
            },
            submit() {
                if (this.type == 'input') this.keyword = this.keyword ? this.keyword : '';
                this.state = false;
                this.$emit('change', this.keyword);
            },
        },
    };
</script>

<style>
    .box {
        width: 100%;
        height: 100%;
        /* overflow: auto; */
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .box-left-search {
        width: calc(100% - 40px);
    }
    .box-left {
        width: calc(100% - 22px);
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    .box-right {
        /* flex: 1; */
    }
    .box-right i {
        margin-left: 5px;
        color: #2d8cf0;
        font-size: 16px;
    }
    .box .hover {
        display: none;
    }
    .box:hover .hover {
        display: inline;
    }
</style>
