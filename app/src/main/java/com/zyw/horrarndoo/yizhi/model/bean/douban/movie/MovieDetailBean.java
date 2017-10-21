package com.zyw.horrarndoo.yizhi.model.bean.douban.movie;


import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.ImagesBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.PersonBean;
import com.zyw.horrarndoo.yizhi.model.bean.douban.movie.child.RatingBean;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/10/16.
 * <p>
 */
public class MovieDetailBean {

    /**
     * rating : {"max":10,"average":6.9,"stars":"35","min":0}
     * reviews_count : 2440
     * wish_count : 22882
     * douban_site :
     * year : 2016
     * images : {"small":"https://img3.doubanio
     * .com/view/movie_poster_cover/ipst/public/p2378133884.jpg","large":"https://img3.doubanio
     * .com/view/movie_poster_cover/lpst/public/p2378133884.jpg","medium":"https://img3.doubanio
     * .com/view/movie_poster_cover/spst/public/p2378133884.jpg"}
     * （更多信息）alt : https://movie.douban.com/subject/26630781/
     * id : 26630781
     * mobile_url : https://movie.douban.com/subject/26630781/mobile
     * title : 我不是潘金莲
     * do_count : null
     * share_url : https://m.douban.com/movie/subject/26630781
     * seasons_count : null
     * schedule_url : https://movie.douban.com/subject/26630781/cinema/
     * episodes_count : null
     * countries : ["中国大陆"]
     * genres : ["剧情","喜剧"]
     * collect_count : 73047
     * casts : [{"alt":"https://movie.douban.com/celebrity/1050059/",
     * "avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1691.jpg",
     * "large":"https://img3.doubanio.com/img/celebrity/large/1691.jpg","medium":"https://img3
     * .doubanio.com/img/celebrity/medium/1691.jpg"},"name":"范冰冰","id":"1050059"},
     * {"alt":"https://movie.douban.com/celebrity/1274274/","avatars":{"small":"https://img3
     * .doubanio.com/img/celebrity/small/39703.jpg","large":"https://img3.doubanio
     * .com/img/celebrity/large/39703.jpg","medium":"https://img3.doubanio
     * .com/img/celebrity/medium/39703.jpg"},"name":"郭涛","id":"1274274"},{"alt":"https://movie
     * .douban.com/celebrity/1324043/","avatars":{"small":"https://img3.doubanio
     * .com/img/celebrity/small/58870.jpg","large":"https://img3.doubanio
     * .com/img/celebrity/large/58870.jpg","medium":"https://img3.doubanio
     * .com/img/celebrity/medium/58870.jpg"},"name":"大鹏","id":"1324043"},{"alt":"https://movie
     * .douban.com/celebrity/1275044/","avatars":{"small":"https://img3.doubanio
     * .com/img/celebrity/small/28615.jpg","large":"https://img3.doubanio
     * .com/img/celebrity/large/28615.jpg","medium":"https://img3.doubanio
     * .com/img/celebrity/medium/28615.jpg"},"name":"张嘉译","id":"1275044"}]
     * current_season : null
     * original_title : 我不是潘金莲
     * summary : 一个普通的农村妇女李雪莲（范冰冰
     * 饰），为了纠正一句话，与上上下下、方方面面打了十年交道。打交道的过程中，她没想到一件事变成了另一件事，接着变成了第三件事。十年过去，她没有把这句话纠正过来，但她饱尝了世间的人情冷暖，悟明白了另外一个道理。李雪莲要纠正的这句话是她前夫说的。她前夫说：你是李雪莲吗？我咋觉得你是潘金莲呢？李雪莲要告诉大家的是：我不是潘金莲。©豆瓣
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1274255/",
     * "avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/45667.jpg",
     * "large":"https://img1.doubanio.com/img/celebrity/large/45667.jpg","medium":"https://img1
     * .doubanio.com/img/celebrity/medium/45667.jpg"},"name":"冯小刚","id":"1274255"}]
     * comments_count : 36102
     * ratings_count : 68772
     * aka : ["我是李雪莲","我叫李雪莲","I Am Not Madame Bovary"]
     */

    private RatingBean rating;
    private int reviews_count;
    private int wish_count;
    private String douban_site;
    private String year;
    private ImagesBean images;
    private String alt;
    private String id;
    private String mobile_url;
    private String title;
    private int do_count;
    private String share_url;
    private int seasons_count;
    private String schedule_url;
    private int episodes_count;
    private int collect_count;
    private String current_season;
    private String original_title;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private List<String> countries;
    private List<String> genres;
    private List<PersonBean> casts;
    private List<PersonBean> directors;
    private List<String> aka;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDo_count() {
        return do_count;
    }

    public void setDo_count(int do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(int seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public int getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(int episodes_count) {
        this.episodes_count = episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(String current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<PersonBean> getCasts() {
        return casts;
    }

    public void setCasts(List<PersonBean> casts) {
        this.casts = casts;
    }

    public List<PersonBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<PersonBean> directors) {
        this.directors = directors;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    @Override
    public String toString() {
        return "MovieDetailBean{" +
                "rating=" + rating +
                ", reviews_count=" + reviews_count +
                ", wish_count=" + wish_count +
                ", douban_site='" + douban_site + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                ", mobile_url='" + mobile_url + '\'' +
                ", title='" + title + '\'' +
                ", do_count=" + do_count +
                ", share_url='" + share_url + '\'' +
                ", seasons_count=" + seasons_count +
                ", schedule_url='" + schedule_url + '\'' +
                ", episodes_count=" + episodes_count +
                ", collect_count=" + collect_count +
                ", current_season='" + current_season + '\'' +
                ", original_title='" + original_title + '\'' +
                ", summary='" + summary + '\'' +
                ", subtype='" + subtype + '\'' +
                ", comments_count=" + comments_count +
                ", ratings_count=" + ratings_count +
                ", countries=" + countries +
                ", genres=" + genres +
                ", casts=" + casts +
                ", directors=" + directors +
                ", aka=" + aka +
                '}';
    }

    /**
     * 获取城市字符串
     * @return 城市字符串
     */
    public String getCountriesString(){
        return sListToString(getCountries());
    }

    /**
     * 获取导演字符串
     *
     * @return 导演字符串 A/B/C..
     */
    public String getDirectorsString() {
        return pListToString(getDirectors());
    }

    /**
     * 获取演员字符串
     *
     * @return 演员字符串 A/B/C..
     */
    public String getActorsString() {
        return pListToString(getCasts());
    }

    /**
     * 获取类型字符串
     *
     * @return 类型字符串 A/B/C..
     */
    public String getGenresString() {
        return sListToString(getGenres());
    }

    /**
     * 获取其它名称字符串
     *
     * @return 类型字符串 A/B/C..
     */
    public String getAkaString() {
        return sListToString(getAka());
    }

    /**
     * 格式化list为字符串
     *
     * @param list 类型list
     * @return 字符串 A/B/C..
     */
    private String sListToString(List<String> list) {
        String str = "";
        if (list.size() == 0)
            return str;
        for (int i = 0; i < list.size(); i++) {
            str = str + list.get(i);
            if (i < list.size() - 1)
                str += " / ";
        }
        return str;
    }

    /**
     * 格式化list为字符串
     *
     * @param list 类型list
     * @return 字符串 A/B/C..
     */
    private String pListToString(List<PersonBean> list) {
        String str = "";
        if (list.size() == 0)
            return str;
        for (int i = 0; i < list.size(); i++) {
            str = str + list.get(i).getName();
            if (i < list.size() - 1)
                str += " / ";
        }
        return str;
    }
}
