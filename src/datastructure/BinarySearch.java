package datastructure;

import java.util.Arrays;
import java.util.Random;

public class BinarySearch {
    /*
    * 在有序数组中寻找一个整数
    * @param nums，有序数组
    * @param target，需要寻找的目标
    * @return 找到的数值索引，未找到返回 -1
    * */
    public static int binarySearchNum(int[] nums, int target) {
        int left = 0, right = nums.length - 1; // 搜索区间为 [left, right]
        while (left <= right) {//重点：因为左右闭区间，所以当 left == right 时，还有一个值需要判断，需要加上等号
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1; //搜索区间变成[mid + 1, right]
            } else {
                right = mid - 1; //因为右闭区间,搜索区间为[left, mid - 1]
            }
        }
        return -1;
    }

    public static int binarySearchNum2(int[] nums, int target) {
        int left = 0, right = nums.length; // 搜索区间为 [left, right)
        while (left < right) { //左闭右开区间，当 left == right 时，区间内已经没有值
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;//搜索区间变成[mid + 1, right)
            } else {
                //因为右开区间，搜索区间为[left, mid)，如果为 right = mid - 1，
                //区间为[left, mid - 1)，我们会漏掉 mid - 1位置的值
                right = mid;
            }
        }
        return -1;
    }

    public static int binarySearchLeftBoundNum(int[] nums, int target) {
        int left = 0, right = nums.length; // 搜索区间为 [left, right)
        while (left < right) { //左闭右开区间，当 left == right 时，区间内已经没有值
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid;//右侧逼近，下一个搜索区间[left, mid)
            } else if (nums[mid] < target) {
                left = mid + 1;//搜索区间变成[mid + 1, right]
            } else {
                //因为右开区间，搜索区间为[left, mid)，如果为 right = mid - 1，
                //区间为[left, mid - 1)，我们会漏掉 mid - 1位置的值
                right = mid;
            }
        }
        //目前的 nums[left]是从左至右第一个大于等于 target 的值
        if(left == nums.length){//target 大于所有值
            return -1;
        }else{
            return nums[left] == target ? left : -1;
        }
    }

    public static int binarySearchRightBoundNum(int[] nums, int target) {
        int left = 0, right = nums.length; // 搜索区间为 [left, right)
        while (left < right) { //左闭右开区间，当 left == right 时，区间内已经没有值
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                left = mid + 1;//左侧逼近，下一个搜索区间[mid + 1, right)
            } else if (nums[mid] < target) {
                left = mid + 1;//搜索区间变成[mid + 1, right]
            } else {
                //因为右开区间，搜索区间为[left, mid)，如果为 right = mid - 1，
                //区间为[left, mid - 1)，我们会漏掉 mid - 1位置的值
                right = mid;
            }
        }

        // 目前的 nums[left] 左边的部分的值都是大于等于 target
        if(left == 0){//target 小于所有值
            return -1;
        }else{
            //返回最后一个等于 target 值的下标
            return nums[left - 1] == target ? left - 1 : -1;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,2,4,5,3,77,44,33,77,2,3,1,14,100};
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
//        for(int i = 0; i < 5; i++){
//            Random random = new Random(i*80);
//            int num = nums[random.nextInt(14)];
//            System.out.println("left <= right: searching " + num + " index: " + binarySearchNum(nums,num));
//            System.out.println("left < right: searching " + num + " index： " + binarySearchNum2(nums,num));
//        }
//        System.out.println("searching left bound 2 index: "+binarySearchLeftBoundNum(nums,2));
        System.out.println("searching right bound 2 index: "+binarySearchRightBoundNum(nums,2));
//        System.out.println("searching left bound 77 index: "+binarySearchLeftBoundNum(nums,77));
        System.out.println("searching right bound 77 index: "+binarySearchRightBoundNum(nums,77));
//        System.out.println("searching left bound 1 index: "+binarySearchLeftBoundNum(nums,1));
        System.out.println("searching right bound 1 index: "+binarySearchRightBoundNum(nums,1));
    }
}
