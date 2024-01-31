<template>
    <UserContentFieldCom>
        <div class="ui segment">
            <!--失败提示消息-->
            <div v-if="error_message" class="ui negative message">
                <i class="close icon" @click="close_error_message"></i>
                <div class="">{{error_message}}</div>
            </div>

            <form id="blog-form" action="#" method="post" class="ui form">
                <div class="required field">
                    <input type="text" placeholder="标题" v-model="blog_info.title">
                </div>

                <div class="field">
                    <div class="fourteen wide column">
                        <div class="ui left labeled action input">
                            <label class="ui compact teal basic label">分类</label>
                            <div class="ui fluid selection dropdown my-type" @mouseup="typeMenu" >
                                <input type="hidden" value="-1" >
                                <i class="dropdown icon"></i>
                                <div class="default text">{{ blog_info.typeName }}</div>
                                <div class="menu">
                                    <div class="item" v-for="type in type_list.records" :key="type.id" :data-value="type.id"> {{ type.name }} </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="field">
                    <div class="ui left labeled input">
                        <label class="ui teal basic label">首图</label>
                        <input type="text" placeholder="首图引用地址" v-model="blog_info.firstPicture">
                    </div>
                </div>

                <div class="field">
                    <div id="md-content">
<!--                        <textarea  cols="30" rows="10" placeholder="请编辑博客内容..." v-model="blog_info.content"></textarea>-->
                        <div id="markdown1" ref="markdown1" />
                    </div>
                </div>

                <div class="field">
                    <div>博客描述编写</div>
                    <label>
                        <textarea cols="30" rows="5" placeholder="请编辑博客描述..." v-model="blog_info.overview"></textarea>
                    </label>
                </div>

                <div align="right" style="margin-right: 1em">
                    <div class="ui checkbox">
                        <input type="checkbox" id="top" class="hidden" v-model="blog_info.top">
                        <label for="top">置顶</label>
                    </div>
                    &emsp;
                    <div class="ui checkbox">
                        <input type="checkbox" id="comment" class="hidden" v-model="blog_info.comment">
                        <label for="comment">开启评论</label>
                    </div>
                </div>

                <div class="ui right aligned container">
                    <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
                    <button type="button" id="save-btn" class="ui secondary button" @click="noPublish">保存</button>
                    <button type="button" id="publish-btn" class="ui blue button" @click="submit()">发布</button>
                </div>
            </form>
        </div>
    </UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import router from "@/router";
import Cherry from 'cherry-markdown'
import "cherry-markdown/dist/cherry-markdown.min.css";
export default {
    name: "UserBlogInputView",
    components: {UserContentFieldCom,},
    setup() {
        const route = useRoute();
        const error_message = ref("");
        const type_list = reactive({
            records: null,
        });
        const blog_info = reactive({
            id: -1,
            title: "",
            firstPicture: "",
            content_html: "",
            content_markdown: "",
            overview: "",
            top: false,
            comment: true,
            published: true,
            typeName: "",
        });
        const cherryInstance=ref(null);
        const close_error_message = () => {
            error_message.value = "";
        }

        /**
         * @description: 展开类型下拉框
         * @return void
         */
        const typeMenu = () => {
            // eslint-disable-next-line no-undef
            $(".ui.dropdown").dropdown();
        }

        /**
         * @description: API 获取所有标签
         * @return type_list(标签集合)
         */
        const getTypeList = () => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/authorize/types/get/all", {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === 'success') {
                    type_list.records = resp.data.data;
                }
            }).catch(() => {router.push({name:'500'})});
        }

        /**
         * @description: 提交博客
         * @return void
         */
        const submit = () => {
            // eslint-disable-next-line no-undef
            const typeId = $(".ui.dropdown.my-type").dropdown('get value');
            if(typeId !== "-1") blog_info.typeId = typeId;
            blog_info.contentHtml = cherryInstance.value.getHtml();
            blog_info.contentMarkdown = cherryInstance.value.getMarkdown();
            updateBlog(blog_info);
        }

        /**
         * @description: 保存博客
         * @return void
         */
        const noPublish = () => {
            blog_info.published = false;
            // eslint-disable-next-line no-undef
            const typeId = $(".ui.dropdown.my-type").dropdown('get value');
            if(typeId !== "-1") blog_info.typeId = typeId;
            blog_info.contentHtml = cherryInstance.value.getHtml();
            blog_info.contentMarkdown = cherryInstance.value.getMarkdown();
            updateBlog(blog_info);
        }

        /**
         * @description: API 修改博客
         * @param {object} blog
         * @return void
         */
        const updateBlog = (blog) => {
            axios.post(process.env.VUE_APP_API_BASE_URL + "/authorize/blog/update", blog, {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                },
            }).then((resp) => {
                console.log(resp);
                if(resp.data.error === "success") window.history.go(-1);
                else {
                    error_message.value = resp.data.error;
                }
            }).catch(() => {router.push({name:'500'})})
        }

        /**
         * @description: 根据博客Id获取博客
         * @param {number} blog_id
         * @return blog_info(博客的所有信息)
         */
        const getBlog = (blog_id) => {

            axios.get(process.env.VUE_APP_API_BASE_URL + "/authorize/blog/get/one", {
                params: {
                    "blogId" : blog_id,
                },
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    const data = resp.data.data;
                    blog_info.title = data.title;
                    blog_info.typeId = data.typeId;
                    blog_info.firstPicture = data.firstPicture;
                    blog_info.content_html = data.contentHtml;
                    blog_info.content_markdown = data.contentMarkdown;
                    blog_info.overview = data.overview;
                    blog_info.top = data.top;
                    blog_info.comment = data.comment;
                    blog_info.published = true;
                    blog_info.typeName = data.typeName;
                    cherrryInit();
                    document.title = blog_info.title + "_" + document.title;
                }else {
                    let routeUrl = router.resolve({
                        name: '404',
                    })
                    window.open(routeUrl.href, '_self');
                }

            }).catch(() => {router.push({name:'500'})})
        }

        /**
         * @description: 初始化markdown编辑器
         * @return void
         */
        const cherrryInit = () => {
            cherryInstance.value = new Cherry({
                id: 'markdown1',
                value: blog_info.content_markdown,
                emoji: {
                    useUnicode: true,
                },
                editor: {
                    theme: 'default'
                },
                codeBlock: {
                    theme: 'dark', // 默认为深色主题
                    wrap: true, // 超出长度是否换行，false则显示滚动条
                    lineNumber: true, // 默认显示行号
                    copyCode: true, // 是否显示“复制”按钮
                    editCode: true, // 是否显示“编辑”按钮
                    changeLang: true, // 是否显示“切换语言”按钮
                    customRenderer: {
                        // 自定义语法渲染器
                    },
                    mermaid: {
                        svg2img: false, // 是否将mermaid生成的画图变成img格式
                    },
                },
                mathBlock: {
                    engine: 'MathJax', // katex或MathJax
                    src: '',
                    plugins: true, // 默认加载插件
                },
                inlineMath: {
                    engine: 'MathJax', // katex或MathJax
                    src: '',
                },
                header: {
                    /**
                     * 标题的样式：
                     *  - default       默认样式，标题前面有锚点
                     *  - autonumber    标题前面有自增序号锚点
                     *  - none          标题没有锚点
                     */
                    anchorStyle: 'default',
                },
                toolbars: {
                    theme: 'light',
                    toolbar: ['bold', 'italic', 'underline', 'strikethrough', '|', 'color', 'header', '|', 'list', { insert: ['image', 'link', 'br', 'formula', 'toc', 'table', 'line-table', 'bar-table', 'word'] }, 'settings'],
                    bubble: ['bold', 'italic', 'underline', 'strikethrough', 'sub', 'sup', '|', 'size', 'color'],
                    float: ['h1', 'h2', 'h3', '|', 'checklist', 'quote', 'quickTable', 'code'],
                    customMenu: []
                },
                engine: {
                    syntax: {
                        table: {
                            enableChart: false,
                            externals: [ 'echarts' ]
                        },
                    },
                    customSyntax: {}
                },
                externals: {},
                fileUpload(file, callback) {
                    const formData = new FormData();
                    formData.append("file", file);
                    axios.post(process.env.VUE_APP_API_BASE_URL + "/upload/picture/markdown", formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data',
                        },
                    }).then(resp => {
                        if(resp.data.error === 'success') {
                            const picture_url = resp.data.data;
                            callback(picture_url);
                        }

                    })
                }
            })
        }
        onMounted(() => {
            document.title = "修改博客";
            blog_info.id = route.query.id;
            getBlog(blog_info.id)
            getTypeList();
        })

        return {
            error_message,
            type_list,
            blog_info,
            getTypeList,
            close_error_message,
            typeMenu,
            noPublish,
            submit
        }

    }
}
</script>

<style scoped>

</style>
