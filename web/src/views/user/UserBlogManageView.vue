<template>
<UserContentFieldCom>

    <!--搜索栏-->
    <div class="ui secondary segment form" >
        <input type="hidden" name="page">
        <div class="inline fields">
            <!--                    标题-->
            <div class="field">
                <!--                        <label>-->
                <input type="text" name="title" placeholder="标题" v-model="search.title">
                <!--                        </label>-->
            </div>
            <!--                    分类-->
            <div class="field">
                <!--                        <label>-->
                <select class="ui search dropdown" name="typeId" v-model="search.type_id">
                    <option value="-1">选择分类</option>
                    <option value="1">只</option>
                </select>
                <!--                        </label>-->
            </div>
            <!--                    置顶-->
            <div class="field">
                <div class="ui checkbox">
                    <input type="checkbox" id="top" name="top" v-model="search.top">
                    <label for="top" style="cursor:pointer;">置顶</label>
                </div>
            </div>
            <!--                    发布-->
            <div class="field">

                <div class="ui checkbox">
                    <input type="checkbox" id="published" name="published" v-model="search.published">
                    <label for="published" style="cursor:pointer;">未发布</label>
                </div>
            </div>
            <!--                    搜索-->
            <div class="field">
                <button id="search-btn" type="button" class="ui teal basic button" @click="getBlogListSearch"> <i class="search icon"></i>搜索</button>
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
                <tr v-for="blogs in blog_list.records" :key="blogs.id">
                    <td > {{ generateUniqueId() }} </td>
                    <td > {{ blogs.title }} </td>
                    <td > {{ blogs.typeName }} </td>
                    <td > {{ blogs.top ? "是" : "否" }} </td>
                    <td > {{ blogs.published ? "发布" : "未发布" }} </td>
                    <td > {{ blogs.comment ? "开启" : "未开启" }} </td>
                    <td > {{ blogs.createTime }} </td>
                    <td > {{ blogs.updateTime }} </td>
                    <td>
                        <button class="ui mini blue button">编辑</button>
                        <button onclick="return confirm('确定要删除该博客吗？')" class="ui mini red button">删除</button>
                    </td>
                </tr>

                </tbody>

                <tfoot>
                <tr>
                    <th colspan="9">
                        <div class="ui right floated pagination menu">

                            <!--                            上一页-->
                            <a href="#" class="icon item " style="border: none" v-if="blog_page.current !== 1" @click="getBlogList(blog_page.current - 1)">
                                <i class="left chevron icon"></i>
                            </a>
                            <!--                            页码-->
                            <div style="padding: 0" class="ui pagination menu">
                                <a href="#" class="item" > {{blog_page.current}} </a>
                            </div>
                            <!--                            下一页-->
                            <a href="#" class="icon item" style="border: none" v-if="blog_page.current !== blog_page.total" @click="getBlogList(blog_page.current + 1)">
                                <i  class="right chevron icon"></i>
                            </a>

                            <!--                            总页-->
                            <div class="icon item">
                                共<div> {{ blog_page.total }} </div>页
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
import {onMounted, reactive, ref} from "vue";
import axios from "axios";

export default {
    name: "UserBlogManageView",
    components: {UserContentFieldCom},
    setup() {
        const success_message = ref("");
        const error_message = ref("");
        const blog_page = reactive({});
        const blog_list = reactive({});
        const search = reactive({});
        let blog_uid = 1;
        const close_success_message = () => {
            success_message.value = "";
        }
        const close_error_message = () => {
            error_message.value = "";
        }
        const generateUniqueId = () => {
            return blog_uid ++;
        }
        const getBlogList = (page_num) => {
            axios.get("http://127.0.0.1:3000/authorize/blog/get/all", {
                params: {"pageNum": JSON.stringify(page_num)},
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/json",
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    blog_uid = 1;
                    const data = resp.data.data;
                    blog_page.pre = data.prePage;
                    blog_page.next = data.nextPage;
                    blog_page.current = data.pageInfo.current;
                    blog_page.total = data.pages;
                    blog_list.records = data.pageInfo.records;
                }
            }).catch(
            )
        }


        onMounted(() => {
            getBlogList(1);
        })


        return {
            success_message,
            error_message,
            close_success_message,
            close_error_message,
            generateUniqueId,
            getBlogList,
            blog_page,
            blog_list,
            search
        }
    }
}
</script>

<style scoped>

</style>