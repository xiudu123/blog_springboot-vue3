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
                <select class="ui search dropdown" name="typeId"  v-model="search.type_id">
                    <option value="-1"> </option>
                    <option v-for="type in type_list.records" :key="type.id" :value="type.id"> {{ type.name }} </option>
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
                <button id="search-btn" type="button" class="ui teal basic button" @click="getBlogSearch(1)"> <i class="search icon"></i>搜索</button>
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
            <router-link :to="{name : 'user_blog_input'}" class="ui blue basic button">新增</router-link>
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
                        <button class="ui mini blue button" @click="updateBlog(blogs.id)">编辑</button>
                        <button class="ui mini red button" @click="showConfirmDia(blogs.id)">删除</button>
                    </td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="9">
                        <div class="ui right floated pagination menu">

                            <!--                            上一页-->
                            <a href="#" class="icon item " style="border: none" v-if="blog_page.current !== 1" @click="getBlogSearch(blog_page.current - 1)">
                                <i class="left chevron icon"></i>
                            </a>
                            <!--                            页码-->
                            <div style="padding: 0" class="ui pagination menu">
                                <a href="#" class="item" > {{blog_page.current}} </a>
                            </div>
                            <!--                            下一页-->
                            <a href="#" class="icon item" style="border: none" v-if="blog_page.current !== blog_page.total" @click="getBlogSearch(blog_page.current + 1)">
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

            <div class="ui mini basic modal">
                <div class="ui icon header">
                    <i class="archive icon"></i>
                    是否删除
                </div>
                <div class="content">
                    <p>确定要删除这篇博客吗?</p>
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

    </div>

</UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

export default {
    name: "UserBlogManageView",
    components: {UserContentFieldCom},
    setup() {
        const router = useRouter();
        const success_message = ref("");
        const error_message = ref("");
        const blog_page = reactive({});
        const blog_list = reactive({});
        const type_list = reactive({});
        const search = reactive({});
        const isConfirmDia = ref(false);
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

        const showConfirmDia = (blog_id) => {
            // eslint-disable-next-line no-undef
            $('.ui.mini.basic.modal')
                .modal({
                    onShow:function () {
                        console.log(blog_id)
                    },
                    onApprove: () => {
                      console.log("确认");
                      deleteBlog(blog_id);
                    },
                    onDeny: () => {

                    }
                })
                .modal('show');

        }

        const getTypeList = () => {
            axios.get("http://127.0.0.1:3000/authorize/types/get/all", {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                type_list.records = resp.data.data;
            }).catch();
        }

        const getBlogSearch = (page_num) => {
            axios.get("http://127.0.0.1:3000/authorize/blog/get/search", {
                params: {
                    "pageNum": page_num,
                    "title": search.title,
                    "typeId": search.type_id,
                    "top": search.top,
                    "published": search.published
                },
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    blog_uid = 1;
                    const data = resp.data.data;
                    blog_page.pre = data.prePage;
                    blog_page.next = data.nextPage;
                    blog_page.current = data.pageInfo.current;
                    blog_page.total = data.pageInfo.pages;
                    blog_list.records = data.pageInfo.records;
                }
            }).catch(
            )
        }

        const updateBlog = (blog_id) => {
            router.push({
                name: 'user_blog_update',
                query: {
                    id: blog_id
                }
            })
        }

        const deleteBlog = (blog_id) => {
            axios.post("http://127.0.0.1:3000/authorize/blog/delete", blog_id, {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                },
            }).then(() => {
                getBlogSearch(blog_page.current);
                success_message.value = "操作成功";
            }).catch(() => {
                error_message.value = "操作失败, 请稍后再试";
            })
        }

        onMounted(() => {
            getBlogSearch(1);
            getTypeList();
        })


        return {
            success_message,
            error_message,
            close_success_message,
            close_error_message,
            generateUniqueId,
            getBlogSearch,
            getTypeList,
            showConfirmDia,
            updateBlog,
            deleteBlog,
            type_list,
            blog_page,
            blog_list,
            search,
            isConfirmDia
        }
    }
}
</script>

<style scoped>

</style>