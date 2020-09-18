package datastructure;

import java.util.Arrays;
import java.util.Stack;

/**
 * 二分查找
 * */
public class BinarySearch {

    /*
    * 二分查找变种模板
    * int left = 0, right = nums.length - 1;
    * while(left <= right){
    *   int mid = left + (right - left) / 2;
    *   if(nums[mid] ? key){
    *       // ... right = mid - 1;
    *   }else{
    *       // ... left = mid + 1;
    *   }
    * }
    * return xxx;
    *
    * 1. 判断返回 left 还是 right
    *   循环结束的条件为 right < left，且 right = left - 1，如果查找第一个小于等于 target 的元素，那么是值为 target 的元素中
    *   最左边的一次，此时应该返回 left
    * 2. 判断比较符号
    *   如果是查找第一个小于等于 target 的元素，当 nums[mid] == target 时，应该使 right = mid - 1，这样就可以得到最左的值
    *   
    * */
    /**
    * 在有序数组中寻找一个整数
    * */
    public static int binarySearch(int[] nums, int target) {
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

    /**
     * 查找第一个与 target 相等的元素
     * */
    public static int findFirstEqual(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] >= target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        if(left < nums.length && nums[left] == target){
            return left;
        }
        return -1;
    }

    /**
     * 查找最后一个与 target相等的元素
     * */
    public static int findLastEqual(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] <= target){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        if(right >= 0 && nums[right] == target){
            return right;
        }
        return -1;
    }

    /**
     * 查找最后一个等于或者小于 target 的元素
     * */
    public static int findLastEqualSmaller(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] > target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return right;
    }

    /**
     * 查找最后一个小于 target 的元素
     * */
    public static int findLastSmaller(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] >= target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return right;
    }

    /**
     * 查找第一个等于或大于 target 的元素
     * */
    public static int findFirstEqualLarger(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] >= target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return left;
    }
    /**
     * 查找第一个大于 target 的元素
     * */
    public static int findFirstLarger(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] > target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,2,4,5,3,77,44,33,78,2,3,1,14,100};
        Arrays.sort(nums);
        for(int i = 0; i < nums.length; i++){
            if(i < nums.length - 1)
                System.out.print(nums[i] + "(" + i + "), ");
            else
                System.out.println(nums[i] + "(" + i + ")");
        }

        System.out.println("binarySearch: " + binarySearch(nums, 2));
        System.out.println("findFirstEqual: " + findFirstEqual(nums, 2));
        System.out.println("findLastEqual: " + findLastEqual(nums, 2));
        System.out.println("findFirstEqualLarger: " + findFirstEqualLarger(nums, 2));
        System.out.println("findFirstLarger: " + findFirstLarger(nums, 2));
        System.out.println("findLastEqualSmaller: " + findLastEqualSmaller(nums, 2));
        System.out.println("findLastSmaller: " + findLastSmaller(nums, 2));

    }
}
