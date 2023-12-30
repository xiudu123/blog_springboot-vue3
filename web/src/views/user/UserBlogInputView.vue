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
                            <div class="ui fluid selection dropdown my-type"  @mouseup="typeMenu" >
                                <i class="dropdown icon"></i>
                                <div class="default text">选择分类</div>
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
<!--                    <div id="md-content">-->
<!--                        <textarea cols="30" rows="10" placeholder="请编辑博客内容..." v-model="blog_info.content"></textarea>-->
                        <div id="markdown1" ref="markdown1" />
<!--                    </div>-->
                </div>

                <div class="field">
                    <div>博客描述编写</div>
                    <label>
                        <textarea  cols="30" rows="5" placeholder="请编辑博客描述..." v-model="blog_info.overview"></textarea>
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
                    <button type="button" id="publish-btn" class="ui blue button" @click="submit">发布</button>
                </div>
            </form>
        </div>
    </UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import router from "@/router";
import Cherry from 'cherry-markdown'
import "cherry-markdown/dist/cherry-markdown.min.css";
export default {
    name: "UserBlogInputView",
    components: {UserContentFieldCom,},
    setup() {

        const error_message = ref("");
        const type_list = reactive({
            records: null
        });
        const blog_info = reactive({
            title: "",
            type_id: -1,
            firstPicture: "",
            content: "",
            overview: "",
            top: false,
            comment: false,
            published: true,
        });
        const cherryInstance=ref(null);
        const close_error_message = () => {
            error_message.value = "";
        }


        onMounted(() => {
            getTypeList();
            cherrryInit();
            document.title = "添加博客";
        })

        /**
         * @description: 展开类型下拉框
         * @return void
         */
        const typeMenu = () => {
            // eslint-disable-next-line no-undef
            $(".ui.dropdown").dropdown();
        }

        /**
         * @description: 提交博客
         * @return void
         */
        const submit = () => {
            // eslint-disable-next-line no-undef
            blog_info.typeId = $(".ui.dropdown.my-type").dropdown('get value');
            blog_info.contentHtml = cherryInstance.value.getHtml();
            blog_info.contentMarkdown = cherryInstance.value.getMarkdown();
            addBlog(blog_info);
        }

        /**
         * @description: 保存博客
         * @return void
         */
        const noPublish = () => {
            blog_info.published = false;
            // eslint-disable-next-line no-undef
            blog_info.typeId = $(".ui.dropdown.my-type").dropdown('get value');
            blog_info.contentHtml = cherryInstance.value.getHtml();
            blog_info.contentMarkdown = cherryInstance.value.getMarkdown();
            addBlog(blog_info);
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
         * @description: API 添加博客
         * @param {object} blog
         * @return void
         */
        const addBlog = (blog) => {
            axios.post(process.env.VUE_APP_API_BASE_URL + "/authorize/blog/add", blog, {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                },
            }).then((resp) => {
                if(resp.data.error === "success") window.history.go(-1);
                else {
                    error_message.value = resp.data.error;
                    // eslint-disable-next-line no-undef
                    $(window).scrollTo(0, 500);
                }
            }).catch(() => {router.push({name:'500'})})

        }

        /**
         * @description: 初始化编辑器
         * @return void
         */
        const cherrryInit = () => {
            cherryInstance.value = new Cherry({
                id: 'markdown1',
                value: '# welcome to cherry editor!',
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
                    anchorStyle: 'autonumber',
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
                    // console.log(file);
                    // callback("https://oj.swust.edu.cn/assets/images/user/default.png");
                    // callback(process.env.VUE_APP_API_BASE_URL + "/static/img/avatar.jpg");
                    // api.post(file).then(url => {
                    //   callback(url)
                    // })
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