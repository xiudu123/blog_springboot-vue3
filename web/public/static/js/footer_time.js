// -------------------------------------计算并显示网站创建时长------------------------------------------------
function CalculateDate(create_time, current_time){ // 计算运行时间;
    let diff_time = (current_time - create_time) / 1000; //计算时间差,并把毫秒转换成秒
    let time = [0, 0, 0, 0]; // 日，时，分，秒
    time[0] = parseInt((diff_time / 86400).toString()); // 天  24*60*60*1000
    time[1] = parseInt((diff_time / 3600).toString()) - 24 * time[0];    // 小时 60*60 总小时数-过去的小时数=现在的小时数
    time[2] = parseInt((diff_time % 3600 / 60).toString()); // 分钟 -(day*24) 以60秒为一整份 取余 剩下秒数 秒数/60 就是分钟数
    time[3] = parseInt((diff_time % 60).toString());  // 以60秒为一整份 取余 剩下秒数
    return time;
}
setInterval(function (){ // 实时显示页脚营业时间;
    let create_time = new Date('2023-05-25T18:20:00'); // 网站创建时间
    let current_time = new Date(); // 现在时间
    let differ_time = CalculateDate(create_time, current_time);
    document.getElementById("run_time").innerHTML = differ_time[0] + "天" + differ_time[1] + "时" + differ_time[2] + "分" + differ_time[3] + "秒";
}, 1000);
// -----------------------------------------------------------------------------------------------------

