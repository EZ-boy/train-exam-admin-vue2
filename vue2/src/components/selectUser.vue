<template>
    <el-dialog title="选择用户" @open="open" :modal="false" :visible.sync="dialogVisible" width="60%">
        <div>
            <el-form ref="form" :model="form" label-width="80px">
                <el-row :gutter="20">
                    <el-col :span="6">
                        <el-form-item label="所属单位">
                            <el-input v-model="form.orgName" readonly @focus="dialog1 = true" placeholder="请输入"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="用户编号">
                            <el-input v-model="form.userName" placeholder="请输入"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="用户名称">
                            <el-input v-model="form.realName" placeholder="请输入"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6" style="text-align: right">
                        <el-button type="primary" @click="query">查询</el-button>
                        <el-button @click="reset">重置</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </div>
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
                <el-button type="primary" @click="selectedUser">确定</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
            </div>
        </div>
        <org-tree :showDialog.sync="dialog1" @treeSure="sure" :org-code="$store.getters.userInfo.orgNo"></org-tree>
    </el-dialog>
</template>

<script>
    import sysTable from '@/components/SysTable/sysTable';
    import user from '@/api/user';
    export default {
        name: 'gdsSelectUser',
        components: {
            sysTable,
            orgTree: () => import('@/components/orgTree.vue'),
        },
        props: ['showDialog', 'orgNo'],
        data() {
            return {
                dialog1: false,
                form: {
                    userName: '',
                    realName: '',
                    orgNo: this.$store.getters.userInfo.orgNo,
                    orgName: this.$store.getters.userInfo.orgName,
                },
                tableData: [],
                loading: false,
                columnData: [
                    {
                        label: '用户编号',
                        prop: 'userName',
                    },
                    {
                        label: '用户名称',
                        prop: 'realName',
                    },
                    {
                        label: '所属单位',
                        prop: 'orgName',
                    },
                ],
                selectedUserData: [],
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
                this.selectedUserData = data;
            },
            open() {
                this.fetchData();
            },
            sure(data) {
                this.form.orgNo = data.orgNo;
                this.form.orgName = data.orgName;
            },
            query() {
                this.fetchData();
            },
            fetchData() {
                this.loading = true;
                let params = {
                    ...this.form,
                };
                user.getUserListByOrgNo(params).then((res) => {
                    this.tableData = res.response;
                    this.loading = false;
                });
            },
            reset() {
                this.form = {
                    consNo: '',
                    consName: '',
                };
                this.query();
            },
            single(data) {
                this.selectedUserData = data;
            },
            selectedUser() {
                if (this.selectedUserData.length > 0) {
                    this.$emit('user-sures', this.selectedUserData);
                    this.dialogVisible = false;
                } else {
                    this.$message({
                        type: 'warning',
                        message: '请选择用户！',
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
