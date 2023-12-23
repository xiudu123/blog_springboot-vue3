<template>
<UserContentFieldCom>

    <!--搜索栏-->
    <form action="#" method="post" class="ui secondary segment form">
        <div class="inline fields">
            <div class="field">
                <input type="text" name="title" placeholder="分类类型">
            </div>
            <div class="field">
                <div class="ui teal basic button"> <i class="search icon"></i>搜索</div>
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
            <a href="#" class="ui blue basic button">新增</a>
        </div>

        <table class="ui center aligned celled table segment" style="width: 100%">
            <thead>
            <tr>
                <th></th>
                <th>分类类型</th>
                <th>创建时间</th>
                <th>修改时间</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <tr v-for="type in type_list.records" :key="type.id">
                <!--                    <div th:text="${type}"></div>-->
                <td> {{ generateUniqueId() }} </td>
                <td> {{ type.name }} </td>
                <td> {{ type.createTime }} </td>
                <td> {{ type.updateTime }} </td>
                <td>
                    <a href="#" class="ui mini blue button">编辑</a>
                    <a href="#" onclick="return confirm('确定要删除该分类吗？')" class="ui mini red button">删除</a>
                </td>
            </tr>
            </tbody>

            <tfoot>
            <tr>
                <th colspan="5">
                    <div class="ui right floated pagination menu">

                        <!--                            上一页-->
                        <a class="icon item">
                            <i class="left chevron icon"></i>
                        </a>
                        <!--                            页码-->
                        <div  style="padding: 0" class="ui pagination menu">
                            <a class="item">1</a>
                        </div>
                        <!--                            下一页-->
                        <a class="icon item">
                            <i class="right chevron icon"></i>
                        </a>

                        <!--                            总页-->
                        <div class="icon item">
                            共<div>1</div>页
                        </div>

                    </div>
                </th>
            </tr>
            </tfoot>
        </table>
    </div>

</UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
export default {
    name: "UserTypeManage",
    components: {UserContentFieldCom,},
    setup() {
        const error_message = ref("");
        const success_message = ref("");
        const type_list = reactive({});
        let type_id = 1;
        const close_error_message = () => {
            error_message.value = "";
        }
        const close_success_message = () => {
            success_message.value = "";
        }
        const generateUniqueId = () => {
            return type_id ++;
        }
        const getTypeList = () => {
            axios.get("http://127.0.0.1:3000/authorize/types/get/all", {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                type_list.records = resp.data.data;
                console.log(type_list.records)
            }).catch();
        }

        onMounted(() => {
            getTypeList();
        })

        return {
            error_message,
            success_message,
            type_list,
            type_id,
            generateUniqueId,
            getTypeList,
            close_error_message,
            close_success_message
        }
    }
}
</script>

<style scoped>

</style>