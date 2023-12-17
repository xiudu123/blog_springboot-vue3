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
            <tr>
                <!--                    <div th:text="${type}"></div>-->
                <td>1</td>
                <td>codeforces</td>
                <td>2023-5-28</td>
                <td>2023-5-28</td>
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
import {ref} from "vue";
export default {
    name: "UserTypeManage",
    components: {UserContentFieldCom,},
    setup() {
        const error_message = ref("操作失败");
        const success_message = ref("操作成功");
        const close_error_message = () => {
            error_message.value = "";
        }
        const close_success_message = () => {
            success_message.value = "";
        }

        return {
            error_message,
            success_message,
            close_error_message,
            close_success_message
        }
    }
}
</script>

<style scoped>

</style>