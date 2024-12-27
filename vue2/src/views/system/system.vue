<template>
    <div class="sys">
        <div class="sys_con">
            <div class="sys_title">系统支撑服务</div>
            <el-tabs v-model="activeName" stretch @tab-click="handleClick">
                <el-tab-pane name="first">
                    <span slot="label"
                        ><img :src="require(`@/assets/img/system/major${activeName === 'first' ? '_active' : ''}.png`)" alt="" /> 系统专业维护</span
                    >
                    <div v-show="!addMajor">
                        <div class="major_menu">
                            <div style="display: flex; width: 100%; justify-content: flex-end">
                                <el-button type="success" @click="downloadTemplate">下载模板</el-button>
                                <el-upload
                                    class="upload_question"
                                    ref="upload1"
                                    action=""
                                    :http-request="questionBankUpload"
                                    :on-preview="handlePreview"
                                    :on-remove="handleRemove"
                                    :show-file-list="false"
                                    :auto-upload="true"
                                >
                                    <el-button slot="trigger" @click.stop="validate" type="success">上传题库</el-button>
                                </el-upload>
                                <el-button type="success" @click="aMajor">新增专业</el-button>
                            </div>
                        </div>
                        <div v-if="majorList.length > 0" class="major_con">
                            <div
                                :class="['major_item', item.checked ? 'major_item_active' : '']"
                                @click="selectedMajor(item)"
                                v-for="item in majorList1"
                                :key="item.id"
                            >
                                <div style="display: flex">
                                    <img class="major_img" src="@/assets/img/system/icon2.png" alt="" />
                                    <div class="major_title">
                                        <div class="m_title">{{ item.major }}</div>
                                        <div class="m_subtitle">
                                            {{ item.majorDesc === null || item.majorDesc === '' ? '无专业描述' : item.majorDesc }}
                                        </div>
                                    </div>
                                </div>
                                <div class="major_oper">
                                    <img @click.stop="delMajor(item)" src="@/assets/img/system/delete.png" alt="" />
                                    <img @click.stop="editMajor(item)" src="@/assets/img/system/edit.png" alt="" />
                                </div>
                            </div>
                        </div>
                        <el-empty v-if="majorList.length === 0" description="暂无专业"></el-empty>
                    </div>
                    <div v-show="addMajor">
                        <!-- <div class="major_menu" style="justify-content: flex-start">
                            <el-button type="success" @click="downloadTemplate">下载模板</el-button>
                            <el-button type="success">上传题库</el-button>
                        </div> -->
                        <div class="add_major" style="height: 38rem">
                            <div class="major_info">
                                <img class="major_img" src="@/assets/img/system/icon2.png" alt="" />
                                <div class="major_title" style="width: auto">
                                    <div class="m_title1">{{ form.major === '' ? '专业名称' : form.major }}</div>
                                    <div class="m_subtitle1">{{ form.majorDesc === '' ? '专业描述' : form.majorDesc }}</div>
                                </div>
                            </div>
                            <div class="major_add_info" style="height: 30.625rem">
                                <div class="add_title">
                                    <img src="@/assets/img/system/edit.png" alt="" />
                                    <div>专业维护</div>
                                </div>
                                <el-form ref="form" :model="form" label-width="95px">
                                    <el-form-item label="专业类型：" required>
                                        <el-input v-model="form.major"></el-input>
                                    </el-form-item>
                                    <el-form-item label="专业描述：">
                                        <el-input v-model="form.majorDesc"></el-input>
                                    </el-form-item>
                                    <el-form-item
                                        :label="index === 0 ? '主题名称：' : ''"
                                        v-for="(item, index) in form.themeList"
                                        :key="index"
                                        class="aaa"
                                    >
                                        <el-input v-model="item.theme"></el-input>
                                        <i
                                            v-show="form.themeList.length > 1 || form.themeList.length === 0"
                                            class="el-icon-remove-outline add_icon"
                                            @click="removeTheme(item.id, index)"
                                        ></i>
                                        <i
                                            v-show="index === form.themeList.length - 1"
                                            class="el-icon-circle-plus-outline add_icon"
                                            @click="insertTheme"
                                        ></i>
                                    </el-form-item>
                                </el-form>
                                <div class="major_btn">
                                    <el-button @click="cancel">取消</el-button>
                                    <el-button type="primary" @click="insertMajor">确认</el-button>
                                </div>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane name="second">
                    <span slot="label"
                        ><img :src="require(`@/assets/img/system/task${activeName === 'second' ? '_active' : ''}.png`)" alt="" /> 考试任务制定</span
                    >
                    <div v-show="!addTask">
                        <div class="major_menu">
                            <el-form :model="taskSearchForm" class="demo-form-inline">
                                <el-form-item label="专业类型">
                                    <el-select
                                        clearable
                                        @clear="taskMajorChange"
                                        @change="taskMajorChange"
                                        v-model="taskSearchForm.majorId"
                                        placeholder="专业类型"
                                    >
                                        <el-option label="" value=""></el-option>
                                        <el-option v-for="item in majorList" :key="item.id" :label="item.major" :value="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-form>
                            <div style="display: flex">
                                <el-button type="success" @click="aTask">新增任务</el-button>
                            </div>
                        </div>
                        <div style="width: 100%; height: 32.375rem">
                            <div v-show="taskList.length > 0" class="major_con" style="max-height: 32.375rem">
                                <div class="major_item" @click="lookTask(item)" v-for="item in taskList" :key="item.id">
                                    <div style="display: flex">
                                        <img class="major_img" src="@/assets/img/system/icon1.png" alt="" />
                                        <div class="major_title">
                                            <div class="m_title">{{ item.taskName }}</div>
                                            <div class="m_subtitle">{{ item.taskDesc === null || item.taskDesc ? '无任务描述' : item.taskDesc }}</div>
                                        </div>
                                    </div>
                                    <div class="major_oper">
                                        <img @click.stop="delTask(item)" src="@/assets/img/system/delete.png" alt="" />
                                        <img @click.stop="editTask(item)" src="@/assets/img/system/edit.png" alt="" />
                                    </div>
                                </div>
                            </div>
                            <el-empty v-if="taskList.length === 0" description="暂无任务"></el-empty>
                        </div>
                        <div class="box_page">
                            <el-pagination
                                background
                                @size-change="size"
                                @current-change="current"
                                :page-size="pageSize"
                                :current-page.sync="pageIndex"
                                layout="prev, pager, next"
                                :total="total"
                            >
                            </el-pagination>
                        </div>
                    </div>
                    <div v-show="addTask">
                        <div class="add_major" style="height: 38.25rem">
                            <i class="el-icon-close closeIcon" @click="addTask = false"></i>
                            <div class="major_info">
                                <img class="major_img" src="@/assets/img/system/icon1.png" alt="" />
                                <div class="major_title" style="width: auto">
                                    <div class="m_title1">{{ taskForm.taskName === '' ? '考试名称' : taskForm.taskName }}</div>
                                    <div class="m_subtitle1">{{ taskForm.taskDesc === '' ? '考试描述' : taskForm.taskDesc }}</div>
                                </div>
                            </div>
                            <div class="major_add_info myTask" style="height: 30.625rem">
                                <div class="add_title">
                                    <img src="@/assets/img/system/edit.png" alt="" />
                                    <div>编辑考试任务</div>
                                </div>
                                <el-form ref="taskForm" :model="taskForm" :rules="taskRules" label-width="95px">
                                    <el-form-item label="考试名称：" prop="taskName">
                                        <el-input v-model="taskForm.taskName" :disabled="lookT"></el-input>
                                    </el-form-item>
                                    <el-form-item label="考试描述：" prop="taskDesc">
                                        <el-input v-model="taskForm.taskDesc" :disabled="lookT"></el-input>
                                    </el-form-item>
                                    <el-form-item label="选择人员：" prop="usersStr">
                                        <el-input v-model="taskForm.usersStr" :disabled="lookT" readonly @focus="selectUser"></el-input>
                                    </el-form-item>
                                    <el-form-item label="专业类型：" required class="aaa">
                                        <span
                                            v-for="item in majorList"
                                            :class="['major_all', item.checked ? 'major_active' : '']"
                                            @click="item.checked = !item.checked"
                                            :style="{ 'pointer-events': lookT ? 'none' : '' }"
                                            :key="item.id"
                                        >
                                            {{ item.major }}
                                        </span>
                                    </el-form-item>
                                    <el-form-item label="培训课程：" prop="trainTitles">
                                        <el-input v-model="taskForm.trainTitles" :disabled="lookT" readonly @focus="selectTrains"></el-input>
                                    </el-form-item>
                                    <el-form-item label="考试时间：" required>
                                        <el-col :span="6">
                                            <el-form-item prop="limitDate">
                                                <el-date-picker
                                                    :disabled="lookT"
                                                    @change="changeLimitTime"
                                                    value-format="yyyy-MM-dd"
                                                    v-model="taskForm.limitDate"
                                                    type="date"
                                                ></el-date-picker>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="8">
                                            <el-form-item prop="limitTime">
                                                <el-time-picker
                                                    is-range
                                                    :disabled="lookT"
                                                    @change="changeLimitTime"
                                                    v-model="taskForm.limitTime"
                                                    value-format="HH:mm:ss"
                                                    range-separator="至"
                                                    start-placeholder="开始时间"
                                                    end-placeholder="结束时间"
                                                    placeholder="选择时间范围"
                                                >
                                                </el-time-picker>
                                            </el-form-item>
                                        </el-col>
                                    </el-form-item>
                                    <el-form-item label="答题时间：" prop="suggestTime" class="aaa bbb">
                                        <el-input v-model="taskForm.suggestTime" :disabled="lookT" readonly></el-input>
                                        <span class="time_text">分</span>
                                    </el-form-item>
                                    <el-row :gutter="20">
                                        <el-col :span="12" style="display: flex">
                                            <el-form-item label="单选数量：" prop="radioNum">
                                                <el-input v-model.number="taskForm.radioNum" :disabled="lookT" @change="changeScore"></el-input>
                                            </el-form-item>
                                            <el-form-item label="分值：" prop="radioScore">
                                                <el-input v-model.number="taskForm.radioScore" :disabled="lookT" @change="changeScore"></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="12" style="display: flex">
                                            <el-form-item label="多选数量：" prop="multipleNum">
                                                <el-input v-model.number="taskForm.multipleNum" :disabled="lookT" @change="changeScore"></el-input>
                                            </el-form-item>
                                            <el-form-item label="分值：" prop="multipleScore">
                                                <el-input v-model.number="taskForm.multipleScore" :disabled="lookT" @change="changeScore"></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                    <el-row :gutter="20">
                                        <el-col :span="12" style="display: flex">
                                            <el-form-item label="实操数量：" prop="actualOperNum">
                                                <el-input v-model.number="taskForm.actualOperNum" :disabled="lookT" @change="changeScore"></el-input>
                                            </el-form-item>
                                            <el-form-item label="分值：" prop="actualOperScore">
                                                <el-input
                                                    v-model.number="taskForm.actualOperScore"
                                                    :disabled="lookT"
                                                    @change="changeScore"
                                                ></el-input>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="12">
                                            <el-form-item label="总得分：" class="bbb" prop="score">
                                                <el-input v-model.number="taskForm.score" :disabled="lookT" readonly></el-input>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </el-form>
                                <div class="major_btn">
                                    <el-button @click="addTask = false">取消</el-button>
                                    <el-button v-show="!lookT" type="primary" @click="saveTask">确认</el-button>
                                </div>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane name="third">
                    <span slot="label"
                        ><img :src="require(`@/assets/img/system/courseware${activeName === 'third' ? '_active' : ''}.png`)" alt="" /> 课件上传</span
                    >
                    <div>
                        <div class="major_menu">
                            <el-form :model="cwareSearchForm" class="demo-form-inline">
                                <el-form-item label="专业类型">
                                    <el-select
                                        clearable
                                        @clear="getCwareList"
                                        v-model="cwareSearchForm.majorId"
                                        placeholder="专业类型"
                                        @change="getCwareList"
                                    >
                                        <el-option label="" value=""></el-option>
                                        <el-option v-for="item in majorList" :key="item.id" :label="item.major" :value="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-form>
                            <div style="display: flex">
                                <el-button type="success" @click="cwareUp">课件上传</el-button>
                            </div>
                        </div>
                        <div style="width: 100%; height: 31rem">
                            <div v-if="cwareList.length > 0" class="major_con" style="max-height: 31rem">
                                <div class="major_item" v-for="item in cwareList" :key="item.id">
                                    <div style="display: flex">
                                        <img class="major_img" src="@/assets/img/system/icon4.png" alt="" />
                                        <div class="major_title">
                                            <div class="m_title">{{ item.title }}</div>
                                            <div class="m_subtitle">{{ item.subTitle }}</div>
                                        </div>
                                    </div>
                                    <div class="major_oper">
                                        <img @click.stop="delCware(item)" style="cursor: pointer" src="@/assets/img/system/delete.png" alt="" />
                                        <img @click.stop="editCware(item)" style="cursor: pointer" src="@/assets/img/system/edit.png" alt="" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <el-empty v-if="cwareList.length === 0" description="暂无课件"></el-empty>
                        <div class="box_page" style="margin-top: 5px">
                            <el-pagination
                                background
                                @size-change="size1"
                                @current-change="current1"
                                :current-page.sync="page.pageIndex"
                                :page-size="page.pageSize"
                                layout="prev, pager, next"
                                :total="page.total"
                            >
                            </el-pagination>
                        </div>
                        <el-dialog title="课件上传" class="ccc" :visible.sync="cware" width="30%">
                            <el-form ref="cwareForm" :model="cwareForm" :rules="rules" v-loading="cwareFormLoading" label-width="100px">
                                <el-form-item label="课程标题：" prop="title">
                                    <el-input v-model="cwareForm.title" placeholder="课程标题"></el-input>
                                </el-form-item>
                                <el-form-item label="课程描述：" prop="subTitle">
                                    <el-input v-model="cwareForm.subTitle" placeholder="课程描述"></el-input>
                                </el-form-item>
                                <el-form-item label="选择专业：" prop="majorId">
                                    <el-select v-model="cwareForm.majorId" @change="changeMajor" placeholder="专业类型">
                                        <el-option v-for="item in majorList" :key="item.id" :label="item.major" :value="item.id"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="选择主题：">
                                    <el-select v-model="cwareForm.themeId" placeholder="选择主题">
                                        <el-option v-for="item in themeList" :key="item.id" :label="item.theme" :value="item.id"></el-option>
                                        <!--  <el-option label="全部类型" value="00"></el-option>
                                        <el-option label="主题2" value="1"></el-option> -->
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="课程类型：" prop="studyType">
                                    <el-select v-model="cwareForm.studyType" placeholder="课程类型">
                                        <el-option label="必修" value="01"></el-option>
                                        <el-option label="选修" value="02"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="选择课件：" required>
                                    <el-upload
                                        class="upload-demo"
                                        ref="upload"
                                        action=""
                                        :http-request="handleFileUpload"
                                        :on-preview="handlePreview"
                                        :on-remove="handleRemove"
                                        :on-change="changeFile"
                                        :file-list="fileList"
                                        :limit="1"
                                        :multiple="false"
                                        :auto-upload="false"
                                    >
                                        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                                        <!-- <el-button style="margin-left: 10px" size="small" type="success" @click="submitUpload"
                                            >上传到服务器</el-button
                                        >
 -->
                                        <!--  <div slot="tip" class="el-upload__tip">
                                            <el-checkbox-group v-model="cwareForm.checkList">
                                                <el-checkbox label="视频"></el-checkbox>
                                                <el-checkbox label="文档"></el-checkbox>
                                                <el-checkbox label="其他"></el-checkbox>
                                            </el-checkbox-group>
                                        </div> -->
                                    </el-upload>
                                </el-form-item>
                            </el-form>
                            <div class="major_btn" style="display: flex; align-items: center; justify-content: flex-end">
                                <el-button @click="cware = false">取消</el-button>
                                <el-button type="primary" @click="cwareSure">确认</el-button>
                            </div>
                        </el-dialog>
                    </div>
                </el-tab-pane>
                <el-tab-pane name="fourth">
                    <span slot="label"
                        ><img :src="require(`@/assets/img/system/user${activeName === 'fourth' ? '_active' : ''}.png`)" alt="" /> 人员信息维护</span
                    >
                    <div>
                        <div class="major_menu myTask">
                            <el-form :inline="true" label-width="90px" :model="userForm" class="demo-form-inline">
                                <el-form-item label="所属单位：">
                                    <el-input v-model="userForm.orgName" readonly @focus="dialog1 = true" placeholder="所属单位"></el-input>
                                </el-form-item>
                                <el-form-item label="姓名：">
                                    <el-input v-model="userForm.realName" @input="changeUserName"></el-input>
                                </el-form-item>
                            </el-form>
                            <div style="display: flex">
                                <el-button type="success" @click="addUser">添加人员</el-button>
                            </div>
                        </div>
                        <div class="user_con">
                            <div class="user_left">
                                <div class="user_title">人员列表</div>
                                <div class="user_list">
                                    <div class="user_item" v-for="(item, index) in userFilterList" :key="index">
                                        <img class="avatar_img" src="@/assets/img/system/avatar.png" alt="" />
                                        <div class="user1">{{ item.realName }}</div>
                                        <div class="user2">{{ item.orgName }}</div>
                                        <div class="user_oper">
                                            <img @click.stop="editUser(item)" src="@/assets/img/system/edit.png" alt="" />
                                            <img
                                                @click.stop="deleteUser(item)"
                                                style="margin-left: 10px"
                                                src="@/assets/img/system/delete.png"
                                                alt=""
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="user_right">
                                <div class="edit_user" v-show="userList.length > 0 && !addDialog">
                                    <el-form
                                        :model="userEditForm"
                                        :rules="userEditFormRules"
                                        ref="userEditForm"
                                        label-width="100px"
                                        class="demo-ruleForm"
                                    >
                                        <el-form-item label="姓名：" prop="userName">
                                            <el-input v-model="userEditForm.realName"></el-input>
                                        </el-form-item>
                                        <el-form-item label="工号：" prop="userName">
                                            <el-input v-model="userEditForm.userName"></el-input>
                                        </el-form-item>
                                        <el-form-item label="权限：" prop="role">
                                            <el-select v-model="userEditForm.role" placeholder="">
                                                <el-option
                                                    v-for="item in roleList"
                                                    :key="item.dictValue"
                                                    :label="item.dictLabel"
                                                    :value="item.dictValue"
                                                >
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                        <el-form-item label="电话：" prop="phone">
                                            <el-input v-model="userEditForm.phone"></el-input>
                                        </el-form-item>
                                        <el-form-item label="单位：" prop="orgName">
                                            <el-input
                                                readonly
                                                v-model="userEditForm.orgName"
                                                @focus="dialog2 = true"
                                                placeholder="所属单位"
                                            ></el-input>
                                        </el-form-item>
                                    </el-form>
                                    <div style="display: flex; flex: 1; align-items: flex-end; justify-content: flex-end">
                                        <el-button type="primary" @click="submitUser">同步信息</el-button>
                                    </div>
                                </div>
                                <div class="add_user" v-show="addDialog">
                                    <el-form
                                        :model="userAddForm"
                                        ref="userAddForm"
                                        label-width="100px"
                                        v-loading="formLoading"
                                        :rules="userEditFormRules"
                                        class="demo-ruleForm"
                                    >
                                        <el-form-item label="姓名：" prop="realName">
                                            <el-input v-model="userAddForm.realName"></el-input>
                                        </el-form-item>
                                        <el-form-item label="工号：" prop="userName">
                                            <el-input v-model="userAddForm.userName"></el-input>
                                        </el-form-item>
                                        <el-form-item label="权限：" prop="role">
                                            <el-select v-model="userAddForm.role" placeholder="">
                                                <el-option
                                                    v-for="item in roleList"
                                                    :key="item.dictValue"
                                                    :label="item.dictLabel"
                                                    :value="item.dictValue"
                                                >
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                        <el-form-item label="电话：" prop="phone">
                                            <el-input v-model="userAddForm.phone"></el-input>
                                        </el-form-item>
                                        <el-form-item label="单位：" prop="orgName">
                                            <el-input readonly v-model="userAddForm.orgName" @focus="dialog2 = true"></el-input>
                                        </el-form-item>
                                    </el-form>
                                    <div style="display: flex; flex: 1; align-items: flex-end; justify-content: flex-end">
                                        <el-button type="primary" @click="submitUser">添 加</el-button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
        <select-user :show-dialog.sync="dialogUser" @user-sures="userSures" @dv="changeDVUser"></select-user>
        <select-train :show-dialog.sync="dialogTrain" :majorIds="taskForm.majorIds" @train-sures="trainSures" @dv="changeDVTrain"></select-train>
        <org-tree :showDialog.sync="dialog1" @treeSure="sure1" :org-code="$store.getters.userInfo.orgNo"></org-tree>
        <org-tree :showDialog.sync="dialog2" @treeSure="sure2" :org-code="$store.getters.userInfo.orgNo"></org-tree>
    </div>
</template>

<script>
    import user from '@/api/user';
    import { debounce } from '@/utils/index';
    import system from '@/api/system';
    import { v4 as uuidv4 } from 'uuid';
    import axios from 'axios';
    import moment from 'moment';
    export default {
        components: {
            'select-user': () => import('@/components/selectUser.vue'),
            'select-train': () => import('@/components/selectTrain.vue'),
            orgTree: () => import('@/components/orgTree.vue'),
        },
        data() {
            return {
                activeName: 'first',
                pageIndex: 1,
                pageSize: 15,
                total: 0,
                dialogUser: false,
                dialogTrain: false,
                form: {
                    major: '',
                    majorDesc: '',
                    themeList: [{ theme: '' }],
                },
                themeList: [],
                taskSearchForm: {
                    majorId: '',
                },
                cwareSearchForm: {
                    majorId: '',
                },
                cwareList: [],
                userEditForm: {
                    userName: '',
                    userNo: '',
                    phone: '',
                    orgNo: '',
                    orgName: '',
                    role: '',
                },
                operUserType: '',
                formLoading: false,
                cwareFormLoading: false,
                userAddForm: {
                    userName: '',
                    realName: '',
                    phone: '',
                    role: '',
                    orgNo: this.$store.getters.userInfo.orgNo,
                    orgName: this.$store.getters.userInfo.orgName,
                },
                roleList: [],
                userEditFormRules: {
                    userName: [{ required: true, message: '请输入工号', trigger: 'change' }],
                    realName: [{ required: true, message: '请输入姓名', trigger: 'change' }],
                    orgName: [{ required: true, message: '请选择单位', trigger: 'change' }],
                    role: [{ required: true, message: '请选择权限', trigger: 'change' }],
                },
                taskForm: {
                    paperType: '6',
                    taskName: '',
                    taskDesc: '',
                    users: [],
                    usersStr: '',
                    trainIds: '',
                    trainTitles: '',
                    trainTitles: '',
                    majorIds: [],
                    limitDate: '',
                    limitTime: ['08:40:00', '09:40:00'],
                    limitStartTime: '',
                    limitEndTime: '',
                    suggestTime: '',
                    radioNum: '',
                    radioScore: '',
                    multipleNum: '',
                    multipleScore: '',
                    actualOperNum: '',
                    actualOperScore: '',
                    score: 0,
                },
                taskRules: {
                    taskName: [{ required: true, message: '请输入考试标题', trigger: 'blur' }],
                    taskDesc: [{ required: true, message: '请输入考试描述', trigger: 'blur' }],
                    usersStr: [{ required: true, message: '请选择考试人员', trigger: 'change' }],
                    trainIds: [{ required: true, message: '请选择培训课程', trigger: 'change' }],
                    limitTime: [{ type: 'array', required: true, message: '请选择考试时间', trigger: 'change' }],
                    suggestTime: [{ required: true, trigger: 'change' }],
                    radioNum: [
                        { required: true, message: '单选数量必填', trigger: 'blur' },
                        { type: 'number', message: '必须为数字值' },
                    ],
                    radioScore: [
                        { required: true, message: '请输入单选分值', trigger: 'blur' },
                        { type: 'number', message: '必须为数字值' },
                    ],
                    multipleNum: [
                        { required: true, message: '多选数量必填', trigger: 'blur' },
                        { type: 'number', message: '必须为数字值' },
                    ],
                    multipleScore: [
                        { required: true, message: '请输入多选分值', trigger: 'blur' },
                        { type: 'number', message: '必须为数字值' },
                    ],
                    actualOperNum: [
                        { required: true, message: '实操数量必填', trigger: 'blur' },
                        { type: 'number', message: '必须为数字值' },
                    ],
                    actualOperScore: [
                        { required: true, message: '实操数量分值', trigger: 'blur' },
                        { type: 'number', message: '必须为数字值' },
                    ],
                    score: [
                        { required: true, trigger: 'change' },
                        { type: 'number', message: '必须为数字值' },
                    ],
                },
                cwareForm: {
                    userNo: this.$store.getters.userInfo.userName,
                    userId: this.$store.getters.userInfo.id,
                    majorId: '',
                    practicalOperation: '02',
                    themeId: '',
                    studyType: '',
                    checkList: '',
                    title: '',
                    subTitle: '',
                    fileId: '',
                    fileName: '',
                },
                addMajor: false,
                addTask: false,
                majorList: [],
                majorList1: [],
                cware: false,
                fileList: [],
                userForm: {
                    orgNo: this.$store.getters.userInfo.orgNo,
                    orgName: this.$store.getters.userInfo.orgName,
                    realName: '',
                },

                userList: [],
                userFilterList: [],
                addDialog: false,
                dialog1: false,
                dialog2: false,
                rules: {
                    title: [{ required: true, message: '请输入课程标题', trigger: 'blur' }],
                    majorId: [{ required: true, message: '请选择专业类型', trigger: 'change' }],
                    studyType: [{ required: true, message: '请选择课程类型', trigger: 'change' }],
                },
                taskList: [],
                page: {
                    pageIndex: 1,
                    pageSize: 15,
                    total: 0,
                },
                downloadQuestionUrl: '',
                lookT: false,
            };
        },
        computed: {
            selectedMajorList: {
                get() {
                    return this.majorList1.filter((item) => item.checked);
                },
            },
        },
        mounted() {
            this.init();
            this.getSysDict();
        },
        methods: {
            lookTask(data) {
                this.lookT = true;
                system.queryTaskById({ id: data.id }).then((res) => {
                    this.taskForm = res.response;
                    const time1 = res.response.limitStartTime;
                    const time2 = res.response.limitEndTime;
                    const users = res.response.users;
                    const majorIds = res.response.majorIds;
                    this.taskForm.paperType = '6';
                    this.taskForm.limitDate = time1.split(' ')[0];
                    this.$set(this.taskForm, 'limitTime', [time1.split(' ')[1], time2.split(' ')[1]]);
                    let list = [];
                    users.map((item) => {
                        // item.userId = item.id;
                        list.push(item.realName);
                    });
                    this.majorList.map((item) => {
                        item.checked = false;
                        majorIds.map((ite) => {
                            if (item.id === ite) {
                                item.checked = true;
                            }
                        });
                    });
                    this.taskForm.usersStr = list.join();
                    this.addTask = true;
                });
            },
            aTask() {
                this.majorList.map((item) => {
                    item.checked = false;
                });
                this.taskForm = {
                    paperType: '6',
                    taskName: '',
                    taskDesc: '',
                    users: [],
                    usersStr: '',
                    trainIds: '',
                    trainTitles: '',
                    majorIds: [],
                    limitDate: '',
                    limitTime: ['08:40:00', '09:40:00'],
                    limitStartTime: '',
                    limitEndTime: '',
                    suggestTime: '',
                    radioNum: '',
                    radioScore: '',
                    multipleNum: '',
                    multipleScore: '',
                    actualOperNum: '',
                    actualOperScore: '',
                    score: 0,
                };
                this.lookT = false;
                this.addTask = true;
            },
            changeMajor() {
                this.cwareForm.themeId = '';
                if (this.cwareForm.majorId != '') {
                    system.getMajorById({ id: this.cwareForm.majorId }).then((res) => {
                        const { themeList } = res.response;
                        this.themeList = themeList;
                    });
                } else {
                    this.themeList = [];
                }
            },
            delCware(item) {
                this.$confirm(`您确定删除吗?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                })
                    .then(() => {
                        // 确定操作
                        system.deleteCoursewareById({ id: item.id }).then((res) => {
                            this.$message.success(res.response);
                            this.getCwareList();
                        });
                    })
                    .catch(() => {
                        // 取消操作
                        this.$message({
                            type: 'info',
                            message: '已取消',
                        });
                    });
            },
            editCware(item) {
                this.cwareForm = { ...item };
                this.fileList = [{ name: item.fileName }];
                this.cware = true;
            },
            validate() {
                if (this.selectedMajorList.length === 0) {
                    this.$message.warning('请选择一个专业后上传');
                    return false;
                } else {
                    this.$refs.upload1.$refs['upload-inner'].handleClick();
                }
            },
            selectedMajor(item) {
                this.majorList1.map((item) => {
                    item.checked = false;
                });
                item.checked = true;
            },
            //删除
            deleteUser(row) {
                this.$confirm(`您确定删除吗?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                })
                    .then(() => {
                        // 确定操作
                        let _this = this;
                        user.deleteUser(row.id).then((re) => {
                            if (re.code === 1) {
                                this.getUserList();
                                _this.$message.success(re.message);
                            } else {
                                _this.$message.error(re.message);
                            }
                        });
                    })
                    .catch(() => {
                        // 取消操作
                        this.$message({
                            type: 'info',
                            message: '已取消',
                        });
                    });
            },
            //提交用户信息
            submitUser() {
                let _this = this;
                if (this.operUserType === 'add') {
                    this.$refs.userAddForm.validate((valid) => {
                        if (valid) {
                            this.formLoading = true;
                            user.createUser(this.userAddForm)
                                .then((data) => {
                                    if (data.code === 1) {
                                        _this.$message.success(data.message);
                                        _this.formLoading = false;
                                        this.userAddForm = {
                                            userName: '',
                                            realName: '',
                                            phone: '',
                                            orgNo: '',
                                            orgName: '',
                                        };
                                        this.getUserList();

                                        // _this.delCurrentView(_this).then(() => {
                                        // // _this.$router.push('/user/admin/list');
                                        // });
                                    } else {
                                        _this.$message.error(data.message);
                                        _this.formLoading = false;
                                    }
                                })
                                .catch((e) => {
                                    _this.formLoading = false;
                                });
                        } else {
                            return false;
                        }
                    });
                } else {
                    this.$refs.userEditForm.validate((valid) => {
                        if (valid) {
                            this.formLoading = true;
                            user.createUser(this.userEditForm)
                                .then((data) => {
                                    if (data.code === 1) {
                                        _this.$message.success(data.message);
                                        _this.formLoading = false;
                                        this.getUserList();

                                        // _this.delCurrentView(_this).then(() => {
                                        // // _this.$router.push('/user/admin/list');
                                        // });
                                    } else {
                                        _this.$message.error(data.message);
                                        _this.formLoading = false;
                                    }
                                })
                                .catch((e) => {
                                    _this.formLoading = false;
                                });
                        } else {
                            return false;
                        }
                    });
                }
            },
            changeScore() {
                const radio =
                    this.taskForm.radioNum === '' || this.taskForm.radioScore === '' ? 0 : this.taskForm.radioNum * this.taskForm.radioScore;
                const multiple =
                    this.taskForm.multipleNum === '' || this.taskForm.multipleScore === ''
                        ? 0
                        : this.taskForm.multipleNum * this.taskForm.multipleScore;
                const actualOper =
                    this.taskForm.actualOperNum === '' || this.taskForm.actualOperScore === ''
                        ? 0
                        : this.taskForm.actualOperNum * this.taskForm.actualOperScore;
                this.taskForm.score = radio + multiple + actualOper;
            },
            changeLimitTime() {
                if (this.taskForm.limitDate != '' && this.taskForm.limitTime.length > 0) {
                    this.taskForm.limitStartTime = this.taskForm.limitDate + ' ' + this.taskForm.limitTime[0];
                    this.taskForm.limitEndTime = this.taskForm.limitDate + ' ' + this.taskForm.limitTime[1];
                    this.taskForm.suggestTime = Math.abs(moment(this.taskForm.limitEndTime).diff(this.taskForm.limitStartTime, 'minutes'));
                } else {
                    this.taskForm.limitStartTime = '';
                    this.taskForm.limitEndTime = '';
                    this.suggestTime = '';
                }
            },
            editTask(data) {
                this.lookT = false;
                system.queryTaskById({ id: data.id }).then((res) => {
                    this.taskForm = res.response;
                    const time1 = res.response.limitStartTime;
                    const time2 = res.response.limitEndTime;
                    const users = res.response.users;
                    const majorIds = res.response.majorIds;
                    this.taskForm.paperType = '6';
                    this.taskForm.limitDate = time1.split(' ')[0];
                    this.$set(this.taskForm, 'limitTime', [time1.split(' ')[1], time2.split(' ')[1]]);
                    let list = [];
                    users.map((item) => {
                        // item.userId = item.id;
                        list.push(item.realName);
                    });
                    this.majorList.map((item) => {
                        item.checked = false;
                        majorIds.map((ite) => {
                            if (item.id === ite) {
                                item.checked = true;
                            }
                        });
                    });
                    this.taskForm.usersStr = list.join();
                    this.addTask = true;
                });
            },
            delTask(data) {
                system.deleteTask({ id: data.id }).then((res) => {
                    if (res.code === 1) {
                        this.$message.success('删除成功');
                        this.getTask();
                    } else {
                        this.$message.error('删除失败');
                    }
                });
            },
            saveTask() {
                this.$refs['taskForm'].validate((valid) => {
                    if (valid) {
                        if (this.taskForm.score != 100) {
                            this.$message.warning('总分必须为100分！');
                            return false;
                        }
                        const list = [];
                        // this.taskForm.majorIds
                        this.majorList.map((item) => {
                            if (item.checked) {
                                list.push(item.id);
                            }
                            this.taskForm.majorIds = list;
                        });
                        if (this.taskForm.majorIds.length === 0) {
                            this.$message.warning('请至少选择一个专业类型！');
                            return false;
                        }
                        const params = {
                            ...this.taskForm,
                        };
                        system.editTask(params).then((res) => {
                            if (res.code === 1) {
                                this.$message.success({
                                    message: '保存成功',
                                    duration: 3000,
                                });
                                this.addTask = false;
                                this.getTask();
                            } else {
                                this.$message.warning(res.message);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            getCwareList() {
                const params = {
                    pageIndex: this.page.pageIndex,
                    pageSize: this.page.pageSize,
                    ...this.cwareSearchForm,
                };
                system.qryCoursewareList(params).then((res) => {
                    this.cwareList = res.response.list;
                    this.page.total = res.response.total;
                });
            },
            taskMajorChange() {
                this.pageIndex = 1;
                this.getTask();
            },
            aMajor() {
                this.form = {
                    major: '',
                    majorDesc: '',
                    themeList: [{ theme: '' }],
                };
                this.addMajor = true;
            },
            changeUserName: debounce(function () {
                let list = JSON.parse(JSON.stringify(this.userList));
                if (this.userForm.realName != '') {
                    this.userFilterList = list.filter((item) => {
                        return item.realName != null && item.realName.indexOf(this.userForm.realName) != -1;
                    });
                } else {
                    this.userFilterList = JSON.parse(JSON.stringify(this.userList));
                }
                if (this.userFilterList.length > 0) {
                    this.addDialog = false;
                    this.userEditForm = this.userFilterList[0];
                } else {
                    this.addUser();
                }
                //this.getUserList();
            }, 500),
            handleClick(tab) {
                if (tab.name === 'first') {
                    this.addMajor = false;
                    this.init();
                } else if (tab.name === 'second') {
                    this.addTask = false;
                    this.getTask();
                } else if (tab.name === 'third') {
                    this.getCwareList();
                } else {
                    this.addDialog = false;
                    this.getRoleList();
                    this.getUserList();
                }
            },
            getTask() {
                const params = {
                    pageIndex: this.pageIndex,
                    pageSize: this.pageSize,
                    ...this.taskSearchForm,
                };
                if (this.taskSearchForm.majorId === '') {
                    system.getTask(params).then((res) => {
                        this.taskList = res.response.list;
                        this.taskTotal = res.response.total;
                    });
                } else {
                    system.getTaskListByMajor(params).then((res) => {
                        this.taskList = res.response.list;
                        this.taskTotal = res.response.total;
                    });
                }
            },
            getUserList() {
                let params = {
                    ...this.userForm,
                };
                user.getUserListByOrgNo(params).then((res) => {
                    this.userList = res.response;
                    this.userFilterList = JSON.parse(JSON.stringify(res.response));
                    if (this.userList.length > 0) {
                        this.userEditForm = this.userList[0];
                    } else {
                        this.addUser();
                    }
                });
            },
            delMajor(data) {
                this.$confirm('即将删除该专业及其相关主题, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                })
                    .then(() => {
                        system.deleteMajor({ id: data.id }).then((res) => {
                            if (res.response === '200') {
                                this.$message.success('删除成功');
                                this.init();
                            } else {
                                this.$message.success(res.response.message);
                            }
                        });
                    })
                    .catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除',
                        });
                    });
            },
            editMajor(data) {
                system.getMajorById({ id: data.id }).then((res) => {
                    let list = [];
                    const { id, major, major_desc, themeList } = res.response;
                    if (themeList.length === 0) {
                        list = [{ theme: '' }];
                    } else {
                        list = themeList;
                    }
                    this.form = {
                        id,
                        major,
                        majorDesc: major_desc,
                        themeList: list,
                    };
                });
                this.addMajor = true;
            },
            cwareUp() {
                this.cwareForm = {
                    userNo: this.$store.getters.userInfo.userName,
                    userId: this.$store.getters.userInfo.id,
                    majorId: '',
                    practicalOperation: '02',
                    themeId: '',
                    studyType: '',
                    checkList: '',
                    title: '',
                    subTitle: '',
                    fileId: '',
                    fileName: '',
                };
                this.fileList = [];
                this.cware = true;
            },
            sure1(data) {
                this.userForm.orgNo = data.orgNo;
                this.userForm.orgName = data.orgName;
                this.getUserList();
            },
            sure2(data) {
                this.userAddForm.orgNo = data.orgNo;
                this.userAddForm.orgName = data.orgName;
                this.userEditForm.orgNo = data.orgNo;
                this.userEditForm.orgName = data.orgName;
                //this.getUserList();
            },

            saveCource(params) {
                system
                    .saveCourseware(this.cwareForm)
                    .then((data) => {
                        if (data.code === 1) {
                            this.$message.success(data.response);
                            (this.cwareForm = {
                                userNo: this.$store.getters.userInfo.userName,
                                majorId: '',
                                practicalOperation: '02',
                                themeId: '',
                                studyType: '',
                                checkList: '',
                                title: '',
                                subTitle: '',
                                fileId: '',
                                fileName: '',
                            }),
                                //this.$message.success(res.response.message);
                                //this.addMajor = false;
                                //this.init();
                                (this.cware = false);
                            this.getCwareList();
                        } else {
                            this.$message.error(data.message);
                        }
                        this.cwareFormLoading = false;
                    })
                    .catch((e) => {
                        this.cwareFormLoading = false;
                    });
            },
            //课件上传确认按钮
            cwareSure() {
                this.$refs['cwareForm'].validate((valid) => {
                    if (valid) {
                        /* if (this.fileList.length === 0) {
                            this.$message.warning('请上传附件');
                            return false;
                        } */
                        this.cwareFormLoading = true;
                        this.$refs.upload.submit();
                    } else {
                        return false;
                    }
                });
            },
            handleFileUpload(file) {
                const uuid = uuidv4();
                let fd = new FormData();
                //把上传文件的添加到 ForDate对象中
                fd.append('file', file.file);
                fd.append('realId', uuid); //定义需要上传其他参数
                axios({
                    method: 'post',
                    url: '/api/file/uploadfile',
                    headers: {
                        //请求头设置
                        'Content-Type': 'multipart/form-data',
                    },
                    data: fd,
                })
                    .then((res) => {
                        let data = res.data;
                        if (data.code === 1) {
                            this.cwareForm.fileId = data.response[0].fileId;
                            this.cwareForm.fileName = data.response[0].fileNameNew;
                            this.saveCource();
                        } else {
                            this.cwareFormLoading = false;
                            this.$message.error(data.message);
                        }
                    })
                    .catch((e) => {
                        this.cwareFormLoading = false;
                    });
            },
            questionBankUpload(file) {
                let fd = new FormData();
                //把上传文件的添加到 ForDate对象中
                fd.append('file', file.file);
                fd.append('majorId', this.selectedMajorList[0].id); //定义需要上传其他参数 专业id
                fd.append('createUser', this.cwareForm.userId + ''); //定义需要上传其他参数 当前登录人
                axios({
                    method: 'post',
                    url: '/api/exam/major/uploadQuestionBank',
                    headers: {
                        //请求头设置
                        'Content-Type': 'multipart/form-data',
                    },
                    data: fd,
                }).then((res) => {
                    this.$message.success(res.data.retMsg);
                });
            },
            userSures(data) {
                console.log('选中用户：', data);
                let list = [];
                data.map((item) => {
                    item.userId = item.id;
                    list.push(item.realName);
                });
                this.taskForm.users = data;
                this.taskForm.usersStr = list.join();
            },
            //用户选择弹框关闭
            changeDVUser(p) {
                this.dialogUser = p;
            },
            trainSures(data) {
                console.log('选中培训课程：', data);
                let list = [];
                let list1 = [];
                data.map((item) => {
                    list.push(item.id);
                    list1.push(item.title);
                });
                this.taskForm.trainIds = list.join();
                this.taskForm.trainTitles = list1.join();
            },
            //用户选择弹框关闭
            changeDVTrain(p) {
                this.dialogTrain = p;
            },
            addUser() {
                this.userAddForm = {
                    userName: '',
                    realName: '',
                    phone: '',
                    orgNo: '',
                    orgName: '',
                    role: '',
                };
                this.addDialog = true;
                this.operUserType = 'add';
            },
            editUser(data) {
                this.addDialog = false;
                this.userEditForm = data;
                this.operUserType = 'edit';
            },
            init() {
                system.getMajor().then((res) => {
                    let arr = res.response;
                    arr.map((item) => {
                        item.checked = false;
                    });
                    this.majorList = JSON.parse(JSON.stringify(arr));
                    this.majorList1 = JSON.parse(JSON.stringify(arr));
                });
            },
            getRoleList() {
                const params = {
                    dictType: 'role_type',
                };
                system.getSysDict(params).then((res) => {
                    res.response.sysDictList.map((item) => {
                        item.dictValue = parseInt(item.dictValue);
                    });
                    this.roleList = res.response.sysDictList;
                });
            },
            getSysDict() {
                const params = {
                    dictType: 'download_question_url',
                };
                system.getSysDict(params).then((res) => {
                    this.downloadQuestionUrl = res.response.sysDictList[0].dictValue;
                });
            },
            removeTheme(id = '', idx) {
                if (id != '') {
                    this.$confirm('即将删除该主题, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning',
                    })
                        .then(() => {
                            this.form.themeList = this.form.themeList.filter((item, index) => index !== idx);
                            system.deleteTheme({ id }).then((res) => {
                                this.$message.success(res.response.message);
                            });
                        })
                        .catch(() => {
                            this.$message({
                                type: 'info',
                                message: '已取消删除',
                            });
                        });
                } else {
                    this.form.themeList = this.form.themeList.filter((item, index) => index !== idx);
                }
            },
            insertTheme() {
                this.form.themeList.push({ theme: '' });
            },
            insertMajor() {
                if (this.form.major === '') {
                    this.$message.warning('请输入专业类型');
                    return;
                }
                if (this.form.id && this.form.id != null && this.form.id != '') {
                    const params = {
                        ...this.form,
                        operNo: this.$store.getters.userInfo.userName,
                    };
                    system.updateMajor(params).then((res) => {
                        this.$message.success(res.response.message);
                        this.addMajor = false;
                        this.init();
                    });
                } else {
                    const params = {
                        ...this.form,
                        operNo: this.$store.getters.userInfo.userName,
                    };
                    system.insertMajor(params).then((res) => {
                        this.$message.success(res.response.message);
                        this.addMajor = false;
                        this.init();
                    });
                }
            },
            downloadTemplate() {
                window.location.href = this.downloadQuestionUrl;
            },
            cancel() {
                this.addMajor = false;
            },
            selectUser() {
                this.dialogUser = true;
            },
            selectTrains() {
                const list = [];
                this.majorList.map((item) => {
                    if (item.checked) {
                        list.push(item.id);
                    }
                    this.taskForm.majorIds = list;
                });
                if (this.taskForm.majorIds.length === 0) {
                    this.$message.error('请至少选择一个专业类型！');
                    return false;
                }
                this.dialogTrain = true;
            },
            submitUpload() {
                this.$refs.upload.submit();
            },
            changeFile(file, fileList) {
            },
            handleRemove(file, fileList) {
            },
            handlePreview(file) {
            },
            size(val) {
                this.pageSize = val;
                this.pageIndex = 1;
                this.getTask();
            },
            current(val) {
                this.pageIndex = val;
                this.getTask();
            },
            size1(val) {
                this.page.pageSize = val;
                this.pageIndex = 1;
                this.getCwareList();
            },
            current1(val) {
                this.page.pageIndex = val;
                this.getCwareList();
            },
        },
    };
</script>

<style scoped lang="scss">
    .sys {
        width: 100%;
        display: flex;
        justify-content: center;
        padding-top: 50px;
    }
    .sys_con {
        width: 1300px;
        height: 750px;
        border: 1px solid #f1f2f3;
        padding: 0 20px;
    }
    .sys_title {
        height: 50px;
        display: flex;
        align-items: center;
        font-size: 20px;
        font-weight: 700;
        color: #1c2227;
        margin-top: 10px;
    }
    /deep/ .el-tabs__item span {
        font-size: 1rem;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    /deep/ .el-tabs__item.is-active span {
        color: #1962f8;
    }
    /deep/ .el-tabs__item img {
        width: 17px;
        height: 17px;
        margin-bottom: 2px;
        margin-right: 10px;
    }
    .major_menu {
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 60px;
    }
    /deep/ .el-form {
        width: 400px;
    }

    .myTask /deep/ .el-form {
        width: 95%;
    }
    .major_menu /deep/ .el-form-item {
        margin-bottom: 0;
    }
    /* .btn {
        margin-left: 20px;
        cursor: pointer;
        color: #606266;
    } */
    .major_con {
        width: 100%;
        display: grid;
        gap: 10px;
        grid-template-columns: repeat(auto-fill, minmax(24.25rem, 1fr));
        max-height: 550px;
        overflow: auto;
        margin-top: 10px;
        padding: 8px;
    }
    .major_item {
        width: 24.25rem;
        height: 110px;
        background-color: #f9fbfc;
        border-radius: 6px;
        border: solid 1px #e7eaec;
        display: flex;
        padding: 15px 10px;
        justify-content: space-between;
        transition: all 0.5s;
        cursor: pointer;
    }
    .major_item_active {
        box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
    }
    .major_item:hover {
        box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
    }
    .major_img {
        width: 136px;
        height: 78px;
    }
    .major_title {
        margin-left: 15px;
        width: 120px;
        .m_title {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            color: #1c2227;
            font-size: 16px;
            height: 30px;
            line-height: 30px;
        }
        .m_subtitle {
            width: 90%;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            color: #8e939f;
            font-size: 14px;
            height: 20px;
            line-height: 20px;
        }
    }
    .m_title1 {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        color: #1c2227;
        font-size: 16px;
        height: 30px;
        line-height: 30px;
    }
    .m_subtitle1 {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        color: #8e939f;
        font-size: 14px;
        height: 20px;
        line-height: 20px;
    }
    .major_oper {
        margin-top: 8px;
    }
    .major_oper img {
        width: 16px;
        height: 16px;
        margin-left: 15px;
        cursor: pointer;
    }
    .add_major {
        width: 100%;
        margin-top: 15px;
        height: 500px;
        border: solid 1px #e7eaec;
        position: relative;
    }
    .major_info {
        padding: 15px;
        background-color: #f9fafc;
        height: 120px;
        border-bottom: 1px solid #e7eaec;
        display: flex;
    }
    .major_add_info {
        padding: 30px;
        height: 378px;
        overflow: auto;
        .add_title {
            font-size: 16px;
            font-weight: bold;
            display: flex;
            align-items: center;
            img {
                margin-right: 15px;
            }
        }
    }
    .major_add_info /deep/ .el-form {
        margin: 30px 0 0 30px;
    }
    .aaa /deep/ .el-form-item__content {
        display: flex;
        align-items: center;
        color: #cacdd9;
    }
    .add_icon {
        cursor: pointer;
        color: #606266;
        font-size: 20px;
        margin-left: 20px;
    }
    .major_btn {
        margin-top: 40px;
        margin-left: 40px;
    }
    .major_all {
        padding: 0 15px;
        border: 1px solid #cacdd9;
        color: #cacdd9;
        border-radius: 20px;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 10px;
        cursor: pointer;
    }
    .major_active {
        color: #fff;
        border: 1px solid #467af0;
        background-color: #467af0;
    }
    .bbb .el-input {
        width: 60px;
    }
    .time_text {
        margin: 0 10px;
    }
    /* /deep/ .el-upload__tip {
        margin-left: 80px;
    }
    /deep/ .el-upload-list {
        margin-left: 80px;
    } */
    .user_con {
        width: 100%;
        margin-top: 20px;
        height: 520px;
        display: flex;
    }
    .user_left {
        width: 400px;
        height: 100%;
        border: 1px solid #edf0f1;
        background: #fff;
        flex-shrink: 0;
        border-radius: 5px;
        padding: 20px 10px;
        display: flex;
        flex-direction: column;
        .user_title {
            height: 30px;
            display: flex;
            align-items: center;
            font-weight: bold;
            flex-shrink: 0;
        }
        .user_list {
            width: 100%;
            display: flex;
            flex-direction: column;
            flex: 1;
            overflow: auto;
            .user_item {
                width: 100%;
                display: flex;
                height: 65px;
                align-items: center;
                flex-shrink: 0;
                .avatar_img {
                    width: 45px;
                    height: 45px;
                    border-radius: 50%;
                    flex-shrink: 0;
                }
                .user1 {
                    color: #000;
                    font-weight: bold;
                    margin-left: 10px;
                    width: 80px;
                }
                .user2 {
                    color: #bac0ce;
                    font-weight: bold;
                    margin-left: 10px;
                    width: 150px;
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                }
                .user_oper {
                    display: flex;
                    align-items: center;
                    justify-content: flex-end;
                    width: 60px;
                    img {
                        cursor: pointer;
                        width: 16px;
                        height: 16px;
                        border-radius: none;
                    }
                }
            }
        }
    }
    .user_right {
        display: flex;
        flex: 1;
        margin-left: 20px;
        flex-direction: column;
        .edit_user {
            width: 100%;
            height: 314px;
            background-color: #fff;
            border: 1px solid #edf0f1;
            padding: 20px;
            display: flex;
        }
        .add_user {
            width: 100%;
            height: 314px;
            background-color: #fff;
            border: 1px solid #edf0f1;
            display: flex;
            padding: 20px;
        }
    }
    .ccc /deep/ .el-select {
        width: 100%;
    }
    .box_page {
        height: 3rem;
        text-align: right;
    }
    /deep/ .box_page .el-pager li {
        width: 36px;
        height: 36px;
        line-height: 36px;
        color: #707173;
        border: solid 1px #a9b1c4;
        background-color: #f9fbfc;
        font-size: 19px;
        font-weight: normal;
    }
    /deep/ .box_page .el-pagination .btn-prev {
        width: 36px;
        height: 36px;
        color: #333;
        border: solid 1px #a9b1c4;
        background-color: #f9fbfc;
    }
    /deep/ .box_page .el-pagination .btn-next {
        width: 36px;
        height: 36px;
        color: #333;
        border: solid 1px #a9b1c4;
        background-color: #f9fbfc;
    }
    /deep/ .box_page .el-pagination .btn-prev .el-icon {
        font-size: 16px;
    }
    /deep/ .box_page .el-pagination .btn-next .el-icon {
        font-size: 16px;
    }
    /deep/ .box_page .el-pagination.is-background .el-pager li:not(.disabled).active {
        color: #1962f8;
        background-color: #e2ebfc;
        border: solid 1px #1962f8;
    }
    /deep/ .el-date-editor .el-range-separator {
        padding: 0;
    }
    .upload_question {
        margin: 0px 0.5rem;
    }
    .closeIcon {
        position: absolute;
        z-index: 1;
        right: 20px;
        top: 20px;
        cursor: pointer;
        font-size: 20px;
    }
    .add_user /deep/ .el-select {
        width: 100%;
    }
    .edit_user /deep/ .el-select {
        width: 100%;
    }
</style>
