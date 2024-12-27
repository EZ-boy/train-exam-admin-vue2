<template>
    <el-dialog title="选择单位" @open="open" :modal="false" :visible.sync="dialogVisible" width="25%" class="treeDialog">
        <el-tree :data="treeData" node-key="orgNo" :props="props" :default-expanded-keys="['62403']" @node-click="handleNodeClick"></el-tree>
        <span slot="footer" class="dialog-footer justify-end flex">
            <el-button class="czbtn" @click="dialogVisible = false">取 消</el-button>
            <el-button class="btn" type="primary" @click="treeSure">确 定</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import user from '@/api/user.js';
    export default {
        name: 'orgTree',
        data() {
            return {
                props: {
                    label: 'orgName',
                    children: 'children',
                    isLeaf: 'isLeaf',
                },
                treeNode: {
                    orgNo: '',
                    orgName: '',
                    orgType: '',
                },
                treeData: [],
            };
        },
        props: ['showDialog', 'orgCode'],
        computed: {
            dialogVisible: {
                get(){
                    return this.showDialog;
                },
                set(val){
                    this.$emit('update:showDialog', val);
                }
            }
        },
        methods: {
            open() {
                this.treeNode = {
                    orgNo: '',
                    orgName: '',
                    orgType: '',
                }
                this.fetchData();
            },
            fetchData() {
                user.queryOrgList({ orgNo: this.orgCode }).then((res) => {
                    this.treeData = res.response;
                });
            },
            handleNodeClick(data) {
                this.treeNode = data;
            },
            treeSure() {
                let flag = false;
                for (const [key, value] of Object.entries(this.treeNode)) {
                    if (key === 'orgNo' && value !== '') {
                        flag = true;
                    }
                }
                if (flag) {
                    this.$emit('treeSure', this.treeNode);
                    this.dialogVisible = false;
                }else{
                    this.$message("请选择一个单位")
                }
            },
        },
    };
</script>

<style scoped lang="scss">
    .treeDialog /deep/ .el-dialog__header {
        padding: 1rem;
        padding-bottom: 1rem;
        background: #f5f7fa;
        box-shadow: inset 0px -1px 0px 0px #ecf1f5;
    }
    .treeDialog /deep/ .el-dialog__body {
        overflow-y: auto;
        height: 500px;
    }
    .treeDialog /deep/.el-tree-node__label {
        font-size: 16px;
    }
    .treeDialog /deep/.el-dialog__body {
        padding-top: 10px;
        padding-bottom: 10px;
    }
    .btn {
        background: #2ca8a4;
        border-radius: 5px;
        font-family: SHS-Normal;
        font-size: 14px;
        color: #ffffff;
        text-align: center;
        font-weight: 400;
        width: 90px;
    }
    .czbtn {
        background: #ffffff;
        border: 1px solid rgba(44, 168, 164, 1);
        border-radius: 5px;

        font-family: SHS-Normal;
        font-size: 14px;
        color: #2ca8a4;
        text-align: center;
        font-weight: 400;
        width: 90px;
    }
</style>
