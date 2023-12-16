<template>
    <ContentFieldCom>

        <div class="ui bottom attached segment">
                <div class="ui form">
                    <div class="field">
                        <label>
                            <textarea name="content" placeholder="请输入评论..."></textarea>
                        </label>
                    </div>
                    <div class="fields">
                        <div class="field m-mobile-wide m-margin-button-small">
                            <div class="ui left icon input">
                                <i class="user icon"></i>
                                <input type="text" name="nickname" placeholder="姓名">
                            </div>

                        </div>
                        <div class="field m-mobile-wide m-margin-button-small">
                            <div class="ui left icon input">
                                <i class="mail icon"></i>
                                <input type="email" name="email" placeholder="邮箱">
                            </div>
                        </div>
                        <div class="field m-mobile-wide m-margin-button-small">
                            <button class="ui blue button m-mobile-wide"><i class="edit icon"></i> 发布</button>
                        </div>
                    </div>
                </div>

                <div class="ui segment">
                    <div class="ui threaded comments">
                        <h3 class="ui dividing header">留言</h3>
                        <div class="comment">
                            <a class="avatar">
                                <img src="@/assets/img/xiu.jpg" alt="">
                            </a>
                            <div class="content">
                                <a class="author">Matt</a>
                                <div class="metadata">
                                    <span class="date">Today at 5:42PM</span>
                                </div>
                                <div class="text">
                                    How artistic!
                                </div>
                                <div class="actions">
                                    <a class="reply">回复</a>
                                </div>
                            </div>
                        </div>
                        <div class="comment">
                            <a class="avatar">
                                <img src="@/assets/img/xiu.jpg" alt="">
                            </a>
                            <div class="content">
                                <a class="author">Elliot Fu</a>
                                <div class="metadata">
                                    <span class="date">Yesterday at 12:30AM</span>
                                </div>
                                <div class="text">
                                    <p>This has been very useful for my research. Thanks as well!</p>
                                </div>
                                <div class="actions">
                                    <a class="reply">回复</a>
                                </div>
                            </div>
                            <div class="comments">
                                <div class="comment">
                                    <a class="avatar">
                                        <img src="@/assets/img/xiu.jpg" alt="">
                                    </a>
                                    <div class="content">
                                        <a class="author">Jenny Hess</a>
                                        <div class="metadata">
                                            <span class="date">Just now</span>
                                        </div>
                                        <div class="text">
                                            Elliot you are always so right :)
                                        </div>
                                        <div class="actions">
                                            <a class="reply">回复</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="comment">
                            <a class="avatar">
                                <img src="@/assets/img/xiu.jpg" alt="">
                            </a>
                            <div class="content">
                                <a class="author">Joe Henderson</a>
                                <div class="metadata">
                                    <span class="date">5 days ago</span>
                                </div>
                                <div class="text">
                                    Dude, this is awesome. Thanks so much
                                </div>
                                <div class="actions">
                                    <a class="reply">回复</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
    </div>

    </ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
export default {
    name: "MessageView",
    components: {ContentFieldCom}
}
</script>

<style scoped>

</style>