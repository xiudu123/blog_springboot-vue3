<template>
    <footer class="ui inverted vertical segment  m-padded-tb m-opacity m-margin-top-big">
        <div class="ui center aligned container">
            <div class="ui inverted divided stackable grid">
                <div class="five wide column">
                    <h4 class="ui inverted header m-opacity-small">联系我</h4>
                    <div class="ui inverted link list">
                        <div class="item">QQ: <span >844506672</span> </div>
                    </div>
                </div>
                <div class="six wide column">
                    <h4 class="ui inverted header m-opacity-small">最新博客</h4>
                    <div id="newBlog-container">
                        <div class="ui inverted link list">
                            <a href="#" target="_blank" class="item">这是一篇博客</a>
                        </div>
                    </div>
                </div>
                <div class="five wide column">
                    <h4 class="ui inverted header m-opacity-small">小栈信息</h4>
                    <div id="message-container">
                        <div class="ui inverted link list">
                            <div class="item">
                                文章总数: <div class="ui orange header m-inline-block">&nbsp;200&nbsp;</div> 篇
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="ui inverted section divider"></div>
            <div class="ui orange header m-text-thin m-text-spaced m-opacity-small">
                我的小栈已营业:
                <span id="run_time"></span>
            </div>

        </div>
    </footer>
</template>

<script>
export default {
    name: "FooterCom"
}
</script>

<style scoped>
.my-footer{
    position: absolute !important;
    bottom: 0 !important;
    width: 100% !important;
}

</style>