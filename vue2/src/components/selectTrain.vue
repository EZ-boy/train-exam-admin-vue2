<template>
    <el-dialog title="选择课程" @open="open" :modal="false" :visible.sync="dialogVisible" width="60%">
        <div style="height: 30rem">
            <sys-table
                selectMode="multiple"
                selection
                :isPage="false"
                :table-height="`calc(100% - 3.5rem)`"
                :table-data="tableData"
                :column-data="columnData"
                :loading="loading"
                table-stripe
                @multiple="multiple"
            ></sys-table>
            <div style="width: 100%; text-align: right">
                <el-button type="primary" @click="selectedTrain">确定</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
            </div>
        </div>
    </el-dialog>
</template>

<script>
    import sysTable from '@/components/SysTable/sysTable';
    import train from '@/api/train';
    export default {
        name: 'gdsSelectUser',
        props: ['showDialog', 'majorIds'],
        components: {
            sysTable,
        },
        data() {
            return {
                tableData: [],
                loading: false,
                columnData: [
                    {
                        label: '培训名称',
                        prop: 'title',
                    },
                    {
                        label: '专业名称',
                        prop: 'major',
                    },
                ],
                selectedData: [],
            };
        },
        computed: {
            dialogVisible: {
                get() {
                    return this.showDialog;
                },
                set(val) {
                    this.$emit('update:showDialog', val);
                },
            },
        },
        methods: {
            multiple(data) {
                this.selectedData = data;
            },
            open() {
                this.fetchData();
            },
            fetchData() {
                this.loading = true;
                let params = {
                    majorIds: this.majorIds,
                };
                train.getTrainsByMajorIds(params).then((res) => {
                    this.tableData = res.response;
                    this.loading = false;
                });
            },
            single(data) {
                this.selectedData = data;
            },
            selectedTrain() {
                if (this.selectedData.length > 0) {
                    this.$emit('train-sures', this.selectedData);
                    this.dialogVisible = false;
                } else {
                    this.$message({
                        type: 'warning',
                        message: '请选择培训课程！',
                    });
                }
            },
        },
    };
</script>

<style lang="scss" scoped>
    .gsjg_table {
        height: 28rem;
    }
    .disp_table_title {
        width: 100%;
        height: 50px;
    }
    .left {
        font-size: 16px;
        color: #1e2733;
        font-weight: 600;
    }
    /deep/ .el-form {
        width: 100%;
    }
</style>
