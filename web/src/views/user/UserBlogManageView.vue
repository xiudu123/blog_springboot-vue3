<template>
<UserContentFieldCom>

    <!--搜索栏-->
    <div class="ui secondary segment form" >
        <input type="hidden" name="page">
        <div class="inline fields">
            <!--                    标题-->
            <div class="field">
                <!--                        <label>-->
                <input type="text" name="title" placeholder="标题">
                <!--                        </label>-->
            </div>
            <!--                    分类-->
            <div class="field">
                <!--                        <label>-->
                <select class="ui search dropdown" name="typeId">
                    <option value="-1">选择分类</option>
                    <option >只</option>
                </select>
                <!--                        </label>-->
            </div>
            <!--                    置顶-->
            <div class="field">
                <div class="ui checkbox">
                    <input type="checkbox" id="top" name="top">
                    <label for="top" style="cursor:pointer;">置顶</label>
                </div>
            </div>
            <!--                    发布-->
            <div class="field">

                <div class="ui checkbox">
                    <input type="checkbox" id="published" name="published">
                    <label for="published" style="cursor:pointer;">未发布</label>
                </div>
            </div>
            <!--                    搜索-->
            <div class="field">
                <button id="search-btn" type="button" class="ui teal basic button"> <i class="search icon"></i>搜索</button>
            </div>
        </div>
    </div>

    <!--成功提示消息-->
    <div v-if="success_message" class="ui success message">
        <i class="close icon" @click="close_success_message"></i>
        <div class="">{{success_message}}</div>
    </div>

    <!--失败提示消息-->
    <div v-if="error_message" class="ui negative message">
        <i class="close icon" @click="close_error_message"></i>
        <div class="">{{error_message}}</div>
    </div>

    <!--文章栏-->
    <div class="ui segments">
        <div class="ui segment right aligned">
            <a href="#" class="ui blue basic button">新增</a>
        </div>

        <div id="table-container">
            <table  class="ui center aligned table segment" style="width: 100%">
                <thead>
                <tr>
                    <th></th>
                    <th>标题</th>
                    <th>类型</th>
                    <th>置顶</th>
                    <th>是否发布</th>
                    <th>开启评论</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                <tr >
                    <td >1</td>
                    <td >这是一个标题</td>
                    <td >codeforces</td>
                    <td >是</td>
                    <td >发布</td>
                    <td >发布</td>
                    <td >2023-5-28</td>
                    <td >2023-5-28</td>
                    <td>
                        <a href="#" class="ui mini blue button">编辑</a>
                        <a href="#" onclick="return confirm('确定要删除该博客吗？')" class="ui mini red button">删除</a>
                    </td>
                </tr>

                </tbody>

                <tfoot>
                <tr>
                    <th colspan="9">
                        <div class="ui right floated pagination menu">

                            <!--                            上一页-->
                            <a class="icon item" onclick="page(this)" >
                                <i class="left chevron icon"></i>
                            </a>
                            <!--                            页码-->
                            <div style="padding: 0 !important;" class="ui pagination menu">
                                <a class="item" onclick="page(this)" >1</a>
                            </div>
                            <!--                            下一页-->
                            <a class="icon item" onclick="page(this)">
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

    </div>

</UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {ref} from "vue";

export default {
    name: "UserBlogManageView",
    components: {UserContentFieldCom},
    setup() {
        const success_message = ref("操作成功");
        const error_message = ref("操作失败");

        const close_success_message = () => {
            success_message.value = "";
        }
        const close_error_message = () => {
            error_message.value = "";
        }
        return {
            success_message,
            error_message,
            close_success_message,
            close_error_message,
        }
    }
}
</script>

<style scoped>

</style>