<template>
<UserContentFieldCom>

    <!--搜索栏-->
    <form action="#" method="post" class="ui secondary segment form">
        <div class="inline fields">
            <div class="field">
                <input type="text" placeholder="分类类型" v-model="type_query_name">
            </div>
            <div class="field">
                <div class="ui teal basic button" @click="getTypeList(1)"> <i class="search icon"></i>搜索</div>
            </div>
        </div>
    </form>

    <!--成功提示消息-->
    <div v-if="success_message" class="ui success message">
        <i class="close icon" @click="close_success_message"></i>
        <div class="">{{success_message}}</div>
    </div>
    <!--失败提示消息-->
    <div v-if="error_message" class="ui negative message" >
        <i class="close icon" @click="close_error_message"></i>
        <div class="">{{error_message}}</div>
    </div>



    <!--分类栏-->
    <div class="ui segments">
        <div class="ui segment right aligned">
            <div class="ui blue basic button" @click="showAddDia()">新增</div>
        </div>

        <table class="ui center aligned celled table segment" style="width: 100%">
            <thead>
            <tr>
                <th></th>
                <th>分类类型</th>
                <th>博客数量</th>
                <th>创建时间</th>
                <th>修改时间</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="(type, index) in type_list.records" :key="type.id">
                <!--                    <div th:text="${type}"></div>-->
                <td> {{ index + 1 }} </td>
                <td> {{ type.name }} </td>
                <td> {{ type.count }} </td>
                <td> {{ type.createTime }} </td>
                <td> {{ type.updateTime }} </td>
                <td>
                    <a href="#" class="ui mini blue button" @click="showUpdateDia(type.id, type.name)">编辑</a>
                    <a href="#" class="ui mini red button" @click="showDeleteDia(type.id)">删除</a>
                </td>
            </tr>
            </tbody>
            <tfoot>
            <tr>
                <th colspan="6">
                    <div class="ui right floated pagination menu">
                        <!--                            上一页-->
                        <a class="icon item" v-if="type_page.current !== 1" @click="getTypeList(type_page.current - 1)">
                            <i class="left chevron icon"></i>
                        </a>
                        <!--                            页码-->
                        <div  style="padding: 0" class="ui pagination menu">
                            <a class="item">{{ type_page.current }}</a>
                        </div>
                        <!--                            下一页-->
                        <a class="icon item" v-if="type_page.current !== type_page.total" @click="getTypeList(type_page.current + 1)">
                            <i class="right chevron icon"></i>
                        </a>

                        <!--                            总页-->
                        <div class="icon item">
                            共<div>{{ type_page.total }}</div>页
                        </div>

                    </div>
                </th>
            </tr>
            </tfoot>
        </table>
        <div class="ui modal my-add">
            <div class="header"> 添加标签 </div>
            <div class="content ui input">
                <input type="text" placeholder="请输入添加的分类名称" v-model="type_post.name">
            </div>
            <div class="actions">
                <div class="ui red cancel inverted button">
                    <i class="remove icon"></i>
                    取消
                </div>
                <div class="ui green ok inverted button">
                    <i class="checkmark icon"></i>
                    确定
                </div>
            </div>
        </div>
        <div class="ui modal my-update">
            <div class="header"> 修改标签 </div>
            <div class="content ui input">
                <input type="text" placeholder="请输入修改的分类名称" v-model="type_post.name">
            </div>
            <div class="actions">
                <div class="ui red cancel inverted button">
                    <i class="remove icon"></i>
                    取消
                </div>
                <div class="ui green ok inverted button">
                    <i class="checkmark icon"></i>
                    确定
                </div>
            </div>
        </div>

        <div class="ui mini basic modal my-delete">
            <div class="ui icon header">
                <i class="archive icon"></i>
                是否删除
            </div>
            <div class="content">
                <p>确定要删除这个标签吗?</p>
            </div>
            <div class="actions">
                <div class="ui red basic cancel inverted button">
                    <i class="remove icon"></i>
                    取消
                </div>
                <div class="ui green ok inverted button">
                    <i class="checkmark icon"></i>
                    确定
                </div>
            </div>
        </div>
    </div>

</UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import router from "@/router";
export default {
    name: "UserTypeManage",
    components: {UserContentFieldCom,},
    setup() {
        const error_message = ref("");
        const success_message = ref("");
        const type_list = reactive({
            records: null
        });
        const type_page = reactive({
            pre : 0,
            next : 0,
            current : 0,
            total : 0
        });
        const type_query_name = ref("");
        const type_post = reactive({
            id: -1,
            name: "",
        });

        const close_error_message = () => {
            error_message.value = "";
        }
        const close_success_message = () => {
            success_message.value = "";
        }

        onMounted(() => {
            document.title = "类型管理";
            getTypeList(1);
        })

        /**
         * @description: 打开添加模态框
         * @return void
         */
        const showAddDia = () => {
            // eslint-disable-next-line no-undef
            $('.ui.modal.my-add')
                .modal({
                    onShow:function () {
                        type_post.id = -1;
                        type_post.name = "";
                    },
                    onApprove: () => {
                        addType(type_post.name);
                        type_post.id = -1;
                        type_post.name = "";
                    },
                    onDeny: () => {
                        type_post.id = -1;
                        type_post.name = "";
                    }
                })
                .modal('show');
        }

        /**
         * @description: 打开修改模态框
         * @param {number} type_id
         * @param {String} type_name
         * @return void
         */
        const showUpdateDia = (type_id, type_name) => {
            // eslint-disable-next-line no-undef
            $('.ui.modal.my-update')
                .modal({
                    onShow:function () {
                        type_post.id = type_id;
                        type_post.name = type_name;
                    },
                    onApprove: () => {

                        updateType(type_post.id, type_post.name);
                        type_post.id = -1;
                        type_post.name = "";
                    },
                    onDeny: () => {
                        type_post.id = -1;
                        type_post.name = "";
                    }
                })
                .modal('show');
        }

        /**
         * @description: 打开删除模态框
         * @param {number} type_id
         * @return void
         */
        const showDeleteDia = (type_id) => {
            // eslint-disable-next-line no-undef
            $('.ui.modal.my-delete')
                .modal({
                    onShow:function () {
                    },
                    onApprove: () => {
                        deleteType(type_id);
                    },
                    onDeny: () => {
                    }
                })
                .modal('show');
        }

        /**
         * @description: API 获取类型列表
         * @param {number} page_num
         * @return type_list(类型列表), type_page(页数信息)
         */
        const getTypeList = (page_num) => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/authorize/types/get/search", {
                params:{
                    "typeName": type_query_name.value,
                    "pageNum": page_num
                },
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === 'success') {
                    const data = resp.data.data;
                    type_list.records = data.records;
                    type_page.pre = data.pagePre;
                    type_page.next = data.pageNext;
                    type_page.current = data.pageCurrent;
                    type_page.total = data.pageTotal;
                }

            }).catch(() => {router.push({name:'500'})});
        }

        /**
         * @description: 添加类型
         * @param {number} type_id
         * @param {String} type_name
         * @return void
         */
        const addType = (type_name) => {
            console.log(type_name);
            axios.post(process.env.VUE_APP_API_BASE_URL + "/authorize/types/add", {name: type_name}, {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    success_message.value = "添加成功";
                    getTypeList(1);
                }else error_message.value = resp.data.error;
            }).catch(() => {router.push({name:'500'})})
        }

        /**
         * @description: 修改类型
         * @param {number} type_id
         * @param {String} type_name
         * @return void
         */
        const updateType = (type_id, type_name) => {
            console.log(type_id);
            console.log(type_name);
            axios.post(process.env.VUE_APP_API_BASE_URL + "/authorize/types/update", {id: type_id, name: type_name}, {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    success_message.value = "修改成功";
                    getTypeList(type_page.current);
                }else error_message.value = resp.data.error;
            }).catch(() => {router.push({name:'500'})})
        }

        /**
         * @description: 删除类型
         * @param {number} type_id
         * @return void
         */
        const deleteType = (type_id) => {
            axios.post(process.env.VUE_APP_API_BASE_URL + "/authorize/types/delete", type_id, {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    success_message.value = "删除成功";
                    getTypeList(type_page.current);
                }else error_message.value = resp.data.error;
            }).catch(() => {router.push({name:'500'})})
        }


        return {
            error_message,
            success_message,
            type_list,
            type_page,
            type_query_name,
            type_post,

            getTypeList,
            close_error_message,
            close_success_message,
            showAddDia,
            showUpdateDia,
            showDeleteDia,
        }
    }
}
</script>

<style scoped>

</style>
