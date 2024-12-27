<template>
    <div class="gsjg_table">
        <el-table
            :class="[selectMode === 'single' ? 'single' : '']"
            ref="myTable"
            :height="tableHeight"
            :max-height="maxHeight"
            size="medium"
            :data="tableData"
            :stripe="tableStripe"
            :row-class-name="rowClass"
            :border="false"
            v-loading="loading"
            @row-click="rowClick"
            @row-dblclick="rowDblclick"
            @current-change="handleRowChange"
            :highlight-current-row="highlightRow"
            @selection-change="handleSelectionChange"
            @select="selectionChange"
            :show-summary="showSummary"
            :summary-method="summaries"
        >
            <!-- 选择框是否开启，selectable控制是否单行禁用 -->
            <el-table-column v-if="selection" type="selection" width="50px" />
            <el-table-column v-if="index" label="序号" type="index" width="50px" align="center" />
            <!-- 普通列 -->
            <el-table-column
                v-for="(column, columIndex) in columnData"
                :key="columIndex"
                :prop="column.prop"
                :label="column.label"
                :width="column.width"
                :fixed="column.fixed"
                :align="column.align || 'center'"
                :sortable="column.sortable || false"
                :index="columIndex"
                show-overflow-tooltip
            >
                <el-table-column
                    :label="subCol.label"
                    :prop="subCol.prop"
                    :align="subCol.align || 'center'"
                    :sortable="subCol.sortable || false"
                    :width="subCol.width"
                    :fixed="subCol.fixed"
                    v-for="(subCol, subIndex) in column.children"
                    :key="subIndex"
                    show-overflow-tooltip
                >
                    <template slot-scope="{ row, $index }">
                        <span
                            v-if="subCol.click"
                            :style="{ color: subCol.color }"
                            style="cursor: pointer"
                            @click="subCol.click(row, $index)"
                            :width="subCol.width"
                            >{{ row[subCol.prop] }}</span
                        >
                        <!-- 自定义内容 -->
                        <span v-else-if="subCol.ownDefined" :width="subCol.width">{{ subCol.ownDefinedReturn(row, $index) }}</span>

                        <span v-else-if="subCol.status" :class="statusList(row[subCol.key])" :width="subCol.width">
                            {{ row[subCol.prop] }}
                        </span>

                        <div v-else-if="subCol.editRow" style="width: 100%; height: 100%">
                            <column-change type="input" :value="row[subCol.prop]" @change="changeNum(row, subCol.prop, $event)" />
                        </div>

                        <span class="pg" v-else-if="subCol.progress">
                            <el-progress :percentage="row[subCol.prop] ? parseFloat(row[subCol.prop]) : 0" :stroke-width="10"></el-progress>
                            <i v-if="subCol.view" class="el-icon-view view" @click="subCol.viewFn(row, $index)"></i>
                        </span>

                        <span v-else-if="subCol.slotName" :width="column.width">
                            <slot :name="subCol.slotName" :row="row">
                                <span>{{ typeof row[subCol.prop] === 'number' ? row[subCol.prop] || 0 : row[columsubColn.prop] || '---' }}</span>
                            </slot>
                        </span>

                        <!-- 操作按钮 -->
                        <span v-else-if="subCol.isOperation" :width="subCol.width">
                            <span v-for="(operations, index) in subCol.operation" :key="index">
                                <el-button
                                    v-if="operations.isShow(row, $index)"
                                    :icon="operations.icon"
                                    :type="operations.type || primary"
                                    @click="operations.buttonClick(row, $index)"
                                    :style="{ color: operations.color }"
                                    size="small"
                                    >{{ operations.label }}
                                </el-button>
                            </span>
                        </span>
                        <!-- 默认展示 -->
                        <span v-else :style="{ color: subCol.color }" :width="subCol.width">{{ row[subCol.prop] }}</span>
                    </template>
                </el-table-column>
                <template slot-scope="{ row, $index }">
                    <span
                        v-if="column.click"
                        :style="{ color: column.color || column.colorFn(row, $index) }"
                        style="cursor: pointer"
                        @click="column.click(row, $index)"
                        :width="column.width"
                        >{{ row[column.prop] }}</span
                    >
                    <!-- 自定义内容 -->
                    <span v-else-if="column.ownDefined" :width="column.width">{{ column.ownDefinedReturn(row, $index) }}</span>

                    <span v-else-if="column.status" :class="statusList(row[column.key])" :width="column.width">
                        {{ row[column.prop] }}
                    </span>

                    <div v-else-if="column.editRow || (column.editRowFn && column.editRowFn(row, $index))" style="width: 100%; height: 100%">
                        <column-change type="input" :value="row[column.prop]" @change="changeNum(row, column.prop, $event)" />
                    </div>

                    <span class="pg" v-else-if="column.progress">
                        <el-progress :percentage="row[column.prop] ? parseFloat(row[column.prop]) : 0" :stroke-width="10"></el-progress>
                        <i v-if="column.view" class="el-icon-view view" @click="column.viewFn(row, $index)"></i>
                    </span>

                    <span v-else-if="column.slotName" :width="column.width">
                        <slot :name="column.slotName" :row="row">
                            <span>{{ typeof row[column.prop] === 'number' ? row[column.prop] || 0 : row[column.prop] || '---' }}</span>
                        </slot>
                    </span>

                    <!-- 操作按钮 -->
                    <span v-else-if="column.isOperation" :width="column.width">
                        <span v-for="(operations, index) in column.operation" :key="index">
                            <el-button
                                v-if="operations.isShow && operations.isShow(row, $index)"
                                :icon="operations.icon"
                                :type="operations.type || 'primary'"
                                @click="operations.buttonClick(row, $index)"
                                :style="{ color: operations.color }"
                                size="small"
                                >{{ operations.label }}
                            </el-button>
                            <div class="cursor-pointer" @click="operations.buttonClick(row, $index)" :style="{ color: operations.color }">
                                {{ operations.label }}
                            </div>
                        </span>
                    </span>
                    <!-- 默认展示 -->
                    <span v-else :style="{ color: column.color }" :width="column.width">{{ row[column.prop] }}</span>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <div v-if="isPage" class="page_div" :style="{ textAlign: page.position || 'right' }">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="page.pageNum"
                :pager-count="7"
                :page-sizes="[15, 30, 50, 100]"
                :page-size="page.pageSize"
                layout="total,sizes,prev, pager, next"
                :total="page.total"
                prev-text="<"
                next-text=">"
            >
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import columnChange from './columnChange.vue';
    export default {
        name: 'gsjgTable',
        components: {
            columnChange,
        },
        props: {
            tableData: {
                type: Array,
                required: true,
            },

            loading: {
                type: Boolean,
            },
            columnData: {
                type: Array,
                required: true,
            },
            tableStripe: {
                type: Boolean,
                required: false,
                default: false,
            },
            index: {
                type: Boolean,
                required: false,
                default: true,
            },
            selection: {
                type: Boolean,
                required: false,
                default: false,
            },
            tableHeight: {
                type: [String, Number],
                default: 'auto',
            },
            maxHeight: {
                type: [String, Number],
            },
            isPage: {
                type: Boolean,
                default: true,
            },
            page: {
                type: Object,
            },
            highlightRow: {
                //是否高亮选中行
                type: Boolean,
                default: false,
            },
            selectMode: {
                type: String,
                default: '',
            },
            showSummary: {
                //是否显示合计
                type: Boolean,
                default: false,
            },
            summaries: {
                type: Function,
            },
        },
        data() {
            return {
                multipleSelection: [],
                selectRow: [],
            };
        },
        watch: {
            //监听多选的选择状态、数据
            multipleSelection(data) {
                this.selectRow = [];
                if (data.length > 0) {
                    data.forEach((item, index) => {
                        this.selectRow.push(this.tableData.indexOf(item));
                    });
                }
            },
        },
        methods: {
            statusList(val) {
                switch (val) {
                    case '01':
                        return ['status1'];
                    case '02':
                        return ['status2'];
                    case '03':
                        return ['status3'];
                    case '04':
                        return ['status4'];
                    case '05':
                        return ['status5'];
                    case '06':
                        return ['status6'];
                    case '07':
                        return ['status7'];
                    case '08':
                        return ['status8'];
                    case '09':
                        return ['status9'];
                    default:
                        return [];
                }
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
                this.$emit('multiple', this.multipleSelection);
            },
            selectionChange(selection, row) {
                if (this.selectMode === 'single') {
                    if (this.multipleSelection[0] == row) {
                        this.multipleSelection = [];
                        this.$refs['myTable'].clearSelection();
                    } else {
                        this.multipleSelection = [row];
                        this.$refs['myTable'].clearSelection();
                        this.$refs['myTable'].toggleRowSelection(row, true);
                    }
                    this.$emit('single', this.multipleSelection);
                }
            },
            rowClick(row, column) {
                if (this.selectMode === 'single') {
                    if (this.multipleSelection[0] == row) {
                        // 选中的是已有的 取消选中
                        this.multipleSelection = [];
                        this.$refs['myTable'].clearSelection();
                    } else {
                        this.multipleSelection = [row];
                        this.$refs['myTable'].clearSelection();
                        this.$refs['myTable'].toggleRowSelection(row, true);
                    }
                    this.$emit('single', this.multipleSelection);
                }
                if (this.selectMode === 'multiple') {
                    const selected = this.multipleSelection.some((item) => item === row); // 是取消选择还是选中
                    if (!selected) {
                        // 不包含   代表选择
                        this.multipleSelection.push(row);
                        this.$refs.myTable.toggleRowSelection(row, true);
                    } else {
                        // 取消选择
                        const finalArr = this.multipleSelection.filter((item) => {
                            return item !== row;
                        });
                        this.multipleSelection = finalArr; // 取消后剩余选中的

                        this.$refs.myTable.toggleRowSelection(row, false);
                    }
                }
            },
            rowDblclick(row, column) {
                this.$emit('rowDblclick', row);
            },
            //勾选数据行的 Checkbox 时触发的事件
            rowClass({ row, rowIndex }) {
                if (this.selectMode !== '') {
                    if (this.selectRow.includes(rowIndex)) {
                        return 'slecleRowColor ';
                    }
                }
            },
            handleRowChange(currentRow) {
                this.$emit('handleRowChange', currentRow);
            },
            // 条数变化
            handleSizeChange(e) {
                this.$emit('size', e);
            },
            // 页码变化
            handleCurrentChange(e) {
                this.$emit('current', e);
            },
            changeNum(row, key, val) {
                row[key] = val;
                this.$emit('changeNum', row, val, key);
            },

            getSummaries(param) {
                this.$emit('getSummaries', param);
            },
        },
    };
</script>
<style lang="scss" scoped>
    .status1 {
        font-size: 14px;
        color: #4b93f2;
        background: rgba(75, 147, 242, 0.1);
        border: 1px solid rgba(75, 147, 242, 0.5);
        border-radius: 12px;
        padding: 0 0.5rem;
        display: inline-block;
    }
    .status2 {
        font-size: 14px;
        color: #139a00;
        background: rgba(30, 152, 48, 0.1);
        border: 1px solid rgba(30, 152, 48, 0.5);
        border-radius: 12px;
        padding: 0 0.5rem;
    }
    .status3 {
        font-size: 14px;
        color: #e24caa;
        background: rgba(255, 16, 166, 0.1);
        border: 1px solid rgba(255, 83, 191, 0.5);
        border-radius: 12px;
        padding: 0 0.5rem;
    }
    .status4 {
        font-size: 14px;
        color: #8b40db;
        background: rgba(139, 64, 219, 0.1);
        border: 1px solid rgba(139, 64, 219, 0.5);
        border-radius: 12px;
        padding: 0 0.5rem;
    }
    .status5 {
        font-size: 14px;
        color: #48b0b8;
        background: rgba(72, 176, 184, 0.1);
        border: 1px solid rgba(72, 176, 184, 0.5);
        border-radius: 12px;
        padding: 0 0.5rem;
    }
    .status6 {
        font-size: 14px;
        color: #ff9400;
        background: rgba(255, 148, 0, 0.1);
        border: 1px solid rgba(255, 148, 0, 0.5);
        border-radius: 12px;
        padding: 0 0.5rem;
    }
    .status7 {
        font-size: 14px;
        color: #ff0000;
        background: rgba(255, 0, 0, 0.1);
        border: 1px solid rgba(255, 0, 0, 0.5);
        border-radius: 12px;
        padding: 0 0.5rem;
    }
    .status8 {
        font-size: 14px;
        color: #a8b2bc;
        background: rgba(155, 155, 155, 0.1);
        border: 1px solid rgba(188, 188, 188, 0.5);
        border-radius: 12px;
        padding: 0 0.5rem;
    }
    .status9 {
        font-size: 14px;
        color: #2196f3;
        background: #bbdefb;
        border: 1px solid #2196f3;
        border-radius: 12px;
        padding: 0 0.5rem;
    }
    .el-button {
        margin: 0 6px;
    }
    /deep/ .el-input--medium .el-input__inner {
        height: 32px;
        line-height: 32px;
    }

    ::v-deep .el-image__inner {
        height: 50px;
    }

    // switch左边文字颜色
    ::v-deep .el-switch__label--left {
        color: #ebf1f5;
    }

    img {
        height: 400px;
    }

    .page_div {
        padding: 0.5rem 0;
    }
    /deep/ .el-table th,
    .el-table tr {
        background-color: unset !important;
    }
    /deep/ .el-table,
    .el-table__expanded-cell {
        background-color: unset !important;
    }
    .gsjg_table /deep/ tbody tr {
        background-color: unset !important;
    }

    .gsjg_table /deep/ thead tr {
        background-color: #f5f7fa;
    }
    /deep/ .el-table th > .cell {
        color: #334458;
        font-weight: 600;
        text-align: center;
    }
    /deep/ .el-table th > .cell::after {
        content: '';
        position: absolute;
        right: 0;
        top: 0;
        bottom: 0;
        background-color: #e3ecf2;
        width: 1px;
    }
    /deep/ .el-table td > .cell {
        color: #334458;
    }
    /deep/ .el-table td {
        border-bottom: 1px solid #ebf1f5 !important;
    }
    /deep/ .el-table th.is-leaf {
        border-bottom: 1px solid #ebf1f5 !important;
    }

    /deep/ .el-table::before {
        height: 0px;
    }

    .pg {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .el-progress {
        display: flex;
        flex: 1;
    }

    .view {
        width: 25px;
        display: inline-block;
        font-size: 16px;
        color: #5cadff;
        padding-bottom: 5px;
        cursor: pointer;
    }
    /deep/ .el-progress__text {
        color: #fff;
    }
    /* /deep/ .el-loading-spinner {
        left: calc((100% - 50px) / 2);
    } */
    /deep/ .el-table .slecleRowColor {
        // background: #edf8f6 !important;
        background: #e6f9ee !important;
    }
    //改变element ui自带的hover高亮颜色
    /deep/ .el-table tbody tr:hover > td {
        //background-color: #edf8f6 !important;
        background-color: #e6f9ee !important;
    }
    /deep/ .slecleRowColor td.el-table__cell {
        background: transparent !important;
    }
    /deep/ .single thead .el-checkbox__input {
        display: none;
    }
    /deep/ .el-table__fixed {
        background-color: #fff;
    }
    /deep/ .el-table__body tr.current-row > td.el-table__cell {
        background-color: #e6f9ee !important;
    }
</style>
